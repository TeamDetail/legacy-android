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
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizService
import com.legacy.legacy_android.res.component.adventure.PolygonStyle


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
    private val getBlockService: GetBlockService,
    private val  userRepository: UserRepository,
    private val getQuizService: GetQuizService
): ViewModel() {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val profileFlow = userRepository.profile

    var minLat: Double = 0.0
    var maxLat: Double = 0.0
    var minLng: Double = 0.0
    var maxLng: Double = 0.0


    var quizStatus = mutableStateOf(0)
    var hintStatus = mutableStateOf<HintStatus>(HintStatus.NO)
    var selectedId = mutableIntStateOf(-1)

    var ruinsData = mutableListOf<RuinsMapResponse>()
    var ruinsIdData = mutableStateOf<RuinsIdResponse?>(null)

    var quizIdData = mutableStateOf<GetQuizResponse?>(null)

    var blockData by mutableStateOf<List<GetBlockResponse>>(emptyList())

    fun isFinite(vararg values: Double): Boolean {
        return values.all { it.isFinite() }
    }



    // ruin 정보 들고오기
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
                Log.d("GetMap", "블럭 불러오기 성공 (중복 제거 후): ${blockData.size}개")
                Log.d("GetMap", "원본 블럭 수: ${rawBlocks.size}개")
            } catch (e: Exception) {
                Log.e("GetMap", "에러: ${e.message}")
            }
        }
    }

    fun fetchRuinsMap(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        if (!isFinite(minLat, maxLat, minLng, maxLng)) {
            return
        }
        viewModelScope.launch {
            try {
                val request = RuinsMapRequest(minLat, maxLat, minLng, maxLng)
                val response = ruinsMapService.ruinsMap(minLat = request.minLat, maxLat = request.maxLat, minLng = request.minLng, maxLng = request.maxLng)
                // 여기서 미리 null 제거
                allRuinsData = response.data.filterNotNull()
                _visibleRuins.clear()
                loadMoreRuins() // 첫 번째 배치 로드
            } catch (e: Exception) {
                Log.e("RuinsMap", "에러: ${e}")
            }
        }
    }

    private fun loadMoreRuins() {
        val currentSize = _visibleRuins.size
        val nextBatch = allRuinsData.drop(currentSize).take(RUINS_BATCH_SIZE)
        _visibleRuins.addAll(nextBatch)
    }

    fun fetchQuiz(ruinsId: Int?){
        viewModelScope.launch {
            try{
                val response = getQuizService.getQuizById(ruinsId)
                quizIdData.value = response.data
                Log.d("Quiz", "성공: ${response.data}")
            }catch (e: Exception){
                println(ruinsId)
                Log.e("GetQuiz", "에러: ${e}")
            }
        }
    }
}
