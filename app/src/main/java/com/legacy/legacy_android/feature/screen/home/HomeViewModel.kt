package com.legacy.legacy_android.feature.screen.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.legacy.legacy_android.feature.network.block.Get.GetBlockResponse
import com.legacy.legacy_android.feature.network.block.Get.GetBlockService
import com.legacy.legacy_android.feature.network.block.Post.PostBlockRequest
import com.legacy.legacy_android.feature.network.block.Post.PostBlockService
import com.legacy.legacy_android.feature.network.ruins.RuinsMapRequest
import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.ruinsId.RuinsIdResponse
import com.legacy.legacy_android.feature.network.ruinsId.RuinsIdService
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizService
import com.legacy.legacy_android.res.component.adventure.PolygonStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerRequest
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerResponse
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerService
import kotlinx.coroutines.CoroutineScope
import okhttp3.Dispatcher

enum class HintStatus {
    NO,
    CREDIT,
    HINT
}

data class Block(
    val blockId: String,
    val latitude: Double,
    val longitude: Double
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ruinsMapService: RuinsMapService,
    private val ruinsIdService: RuinsIdService,
    private val postBlockService: PostBlockService,
    private val postQuizAnswerService: PostQuizAnswerService,
    private val getBlockService: GetBlockService,
    private val userRepository: UserRepository,
    private val getQuizService: GetQuizService
) : ViewModel() {
    val wrongAnswers = mutableListOf<Int>()

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val profileFlow = userRepository.profile

    var minLat: Double = 0.0
    var answerOption = mutableListOf<String>()
    var quizIndex = mutableListOf<Int>()
    var maxLat: Double = 0.0
    var minLng: Double = 0.0
    var maxLng: Double = 0.0

    var quizStatus = mutableStateOf(0)
    var hintStatus = mutableStateOf<HintStatus>(HintStatus.NO)
    var selectedId = mutableIntStateOf(-1)

    var ruinsData = mutableListOf<RuinsMapResponse>()
    var visibleRuins by mutableStateOf<List<RuinsMapResponse>>(emptyList())
    var ruinsIdData = mutableStateOf<RuinsIdResponse?>(null)

    var quizIdData = mutableStateOf<List<GetQuizResponse>?>(null)

    var blockData by mutableStateOf<List<GetBlockResponse>>(emptyList())

    fun isFinite(vararg values: Double): Boolean {
        return values.all { it.isFinite() }
    }

    fun fetchRuinsId(id: Int) {
        viewModelScope.launch {
            try {
                val response = ruinsIdService.getRuinsById(id)
                ruinsIdData.value = response.data
                Log.d("RuinsId", "성공: ${response.data}")
            } catch (e: Exception) {
                Log.e("RuinsId", "에러: ${e.message}")
            }
        }
    }

    fun fetchQuizAnswer() {
        viewModelScope.launch {
            try {
                val requests = answerOption.zip(quizIndex) { answer, id ->
                    PostQuizAnswerRequest(quizId = id, answerOption = answer)
                }
                val response = postQuizAnswerService.answer(requests)
                val results = response.data.results

                wrongAnswers.clear()
                results.forEachIndexed { index, result ->
                    if (!result.isCorrect) {
                        wrongAnswers.add(index)
                    }
                }

                answerOption.clear()

                if (wrongAnswers.isEmpty()) {
                    quizStatus.value = 3
                } else {
                    quizStatus.value = 5
                }

            } catch (e: Exception) {
                quizStatus.value = 5
                Log.e("Answer", "에러 발생: ${e.message}")
            }
        }
    }



    fun fetchBlock(latitude: Double?, longitude: Double?) {
        viewModelScope.launch {
            try {
                if (latitude == null || longitude == null) {
                    Log.e("PostMap", "위치 정보가 없습니다.")
                    return@launch
                }

                if (PolygonStyle.isPointInsideAnyBlock(latitude, longitude, blockData)) {
                    Log.d("PostMap", "현재 위치가 이미 생성된 블록 내부에 있습니다.")
                    return@launch
                }

                val request = PostBlockRequest(
                    latitude = latitude,
                    longitude = longitude,
                )
                val response = postBlockService.block(request)
                Log.d("PostMap", "블록 생성 성공: ${response.blockId}")

            } catch (e: Exception) {
                Log.e("PostMap", "에러: ${e.message}")
            }
        }
    }

    fun fetchGetBlock() {
        viewModelScope.launch {
            try {
                val response = getBlockService.getBlockById()
                val rawBlocks = response.data ?: emptyList()
                blockData = rawBlocks.distinctBy { block ->
                    PolygonStyle.getGridKey(block.latitude, block.longitude)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun fetchRuinsMap(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        if (!isFinite(minLat, maxLat, minLng, maxLng)) return

        viewModelScope.launch {
            try {
                val request = RuinsMapRequest(minLat, maxLat, minLng, maxLng)
                val response = ruinsMapService.ruinsMap(
                    minLat = request.minLat,
                    maxLat = request.maxLat,
                    minLng = request.minLng,
                    maxLng = request.maxLng
                )
                ruinsData = response.data as MutableList<RuinsMapResponse>
                updateVisibleRuinsSmoothly(
                    ruinsData.filter {
                        it.latitude in minLat..maxLat && it.longitude in minLng..maxLng
                    }
                )
            } catch (e: Exception) {
                Log.e("RuinsMap", "에러: ${e.message}")
            }
        }
    }

    fun fetchQuiz(ruinsId: Int?) {
        viewModelScope.launch {
            try {
                Log.d("Quiz", "요청 ID: $ruinsId")
                val response = getQuizService.getQuizById(ruinsId)
                Log.d("Quiz", "응답 결과: ${response.data}")
                println(ruinsId)
                if (!response.data.isNullOrEmpty()) {
                    quizIdData.value = response.data
                }
            } catch (e: Exception) {
                Log.e("GetQuiz", "에러: ${e}")
            }
        }
    }

    fun updateVisibleRuinsSmoothly(inBoundsRuins: List<RuinsMapResponse>) {
        viewModelScope.launch {
            val currentSet = visibleRuins.map { it.ruinsId }.toSet()
            val newRuins = inBoundsRuins.filter { it.ruinsId !in currentSet }
            val chunked = newRuins.chunked(2)

            chunked.forEach { chunk ->
                visibleRuins = visibleRuins + chunk
                delay(50)
            }

            visibleRuins = visibleRuins.filter {
                it.latitude in minLat..maxLat && it.longitude in minLng..maxLng
            }
        }
    }
}
