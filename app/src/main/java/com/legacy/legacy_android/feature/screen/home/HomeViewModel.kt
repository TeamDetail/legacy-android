package com.legacy.legacy_android.feature.screen.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.domain.repository.home.*
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.home.helper.RuinsAnimationHelper
import com.legacy.legacy_android.feature.screen.home.model.QuizStatus
import com.legacy.legacy_android.feature.screen.home.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.commons.lang3.mutable.MutableInt
import javax.inject.Inject

@HiltViewModel
@Stable
class HomeViewModel @Inject constructor(
    private val ruinsRepository: RuinsRepository,
    private val blockRepository: BlockRepository,
    private val quizRepository: QuizRepository,
    private val animationHelper: RuinsAnimationHelper,
    private val userRepository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    var isMapLoaded by mutableStateOf(false)
        private set

    fun setMapLoaded() {
        isMapLoaded = true
    }

    fun fetchProfile(){
        viewModelScope.launch {
            userRepository.clearProfile()
            userRepository.fetchProfile()
        }
    }

    var cameraPosition by mutableStateOf(
        CameraPosition.fromLatLngZoom(LatLng(35.0, 128.0), 14f)
    )

    private val quizAnswers = mutableListOf<QuizAnswer>()

    private var fetchRuinsDetailJob: Job? = null
    private var loadQuizJob: Job? = null
    private var loadCommentJob: Job? = null
    private var searchRuinsJob: Job? = null

    fun updateIsMailOpen(isOpen: Boolean) {
        uiState = uiState.copy(isMailOpen = isOpen)
    }

    fun updateIsCheckOpen(isOpen: Boolean){
        uiState = uiState.copy(isCheckOpen = isOpen)
    }

    fun updateSelectedId(ids: List<Int>) {
        uiState = uiState.copy(selectedId = ids)
    }

    fun updateCommentModal(isOpen: Boolean) {
        uiState = uiState.copy(isCommentModalOpen = isOpen)
    }

    fun updateCommentRate(rate: Int) {
        uiState = uiState.copy(commentRate = rate)
    }

    fun updateIsCommenting(isCommenting: Boolean) {
        uiState = uiState.copy(isCommenting = isCommenting)
    }

    fun updateMapBounds(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        uiState = uiState.copy(
            mapBounds = MapBounds(minLat, maxLat, minLng, maxLng)
        )
    }

    fun updateQuizStatus(status: QuizStatus) {
        uiState = uiState.copy(quizStatus = status)
    }

    fun updateSearchStatus(status: Boolean) {
        uiState = uiState.copy(isSearchRuinOpen = status)
    }

    fun updateHintStatus(status: HintStatus) {
        uiState = uiState.copy(hintStatus = status)
    }

    fun addQuizAnswer(quizId: Int, answer: String) {
        quizAnswers.add(QuizAnswer(quizId, answer))
    }

    fun clearQuizAnswers() {
        quizAnswers.clear()
    }

    fun setCommentValue(value: String) {
        uiState = uiState.copy(commentValue = value)
    }

    fun setSelectedRuinsDetail(ruin: RuinsIdResponse) {
        uiState = uiState.copy(selectedRuinsDetail = ruin)
    }

    fun fetchRuinsDetail(ids: List<Int>) {
        uiState = uiState.copy(ruinsDetail = null)
        uiState = uiState.copy(selectedRuinsDetail = null)
        if (ids.isEmpty()) {
            uiState = uiState.copy(ruinsDetail = null)
            return
        }

        fetchRuinsDetailJob?.cancel()
        fetchRuinsDetailJob = viewModelScope.launch {
            uiState = uiState.copy(loading = true)

            val details = ids.map { id ->
                async { ruinsRepository.getRuinsById(id) }
            }.awaitAll()

            val successDetails = details.mapNotNull { it.getOrNull() }
            uiState = uiState.copy(ruinsDetail = successDetails)

            uiState = uiState.copy(loading = false)
        }
    }
    fun initRuinsDetail(){
        uiState = uiState.copy(ruinsDetail = null)
    }

    fun submitComment() {
        viewModelScope.launch {
            uiState = uiState.copy(commentLoading = true)

            ruinsRepository.postComment(
                uiState.selectedRuinsDetail!!.ruinsId,
                uiState.commentRate,
                uiState.commentValue
            )
                .onSuccess {
                    updateIsCommenting(false)
                    uiState = uiState.copy(commentValue = "")
                    loadCommentById(uiState.selectedRuinsDetail!!.ruinsId)
                }
                .onFailure {
                    println("댓글 작성 실패: $it")
                }
            uiState = uiState.copy(commentLoading = false)
        }
    }

    fun submitQuizAnswers() {
        viewModelScope.launch {
            uiState = uiState.copy(quizStatus = QuizStatus.LOADING)
            quizRepository.submitAnswer(quizAnswers.toList())
                .onSuccess { response ->
                    val wrongIndices = response?.results
                        ?.mapIndexedNotNull { index, result ->
                            if (!result.isCorrect) index else null
                        } ?: emptyList()

                    uiState = uiState.copy(
                        wrongAnswers = wrongIndices,
                        quizStatus = if (wrongIndices.isEmpty()) QuizStatus.SUCCESS else QuizStatus.RETRY
                    )
                    clearQuizAnswers()
                }
                .onFailure {
                }
        }
    }

    fun createBlock(latitude: Double?, longitude: Double?) {
        if (latitude == null || longitude == null) return
        viewModelScope.launch {
            blockRepository.createBlock(latitude, longitude)
        }
    }

    fun loadBlocks() {
        viewModelScope.launch {
            blockRepository.getBlocks()
                .onSuccess { blocks -> uiState = uiState.copy(blocks = blocks) }
        }
    }

    fun searchRuins(name: String) {
        if (name.isBlank()) return

        searchRuinsJob?.cancel()
        searchRuinsJob = viewModelScope.launch {
            uiState = uiState.copy(
                createSearchRuins = null,
                isSearchLoading = true
            )

            ruinsRepository.getSearchRuins(name.trim())
                .onSuccess { ruins ->
                    uiState = uiState.copy(createSearchRuins = ruins)
                }
                .onFailure {
                    println("검색 실패: $it")
                }

            uiState = uiState.copy(isSearchLoading = false)
        }
    }

    private var loadRuinsJob: Job? = null

    fun loadRuinsMap(
        minLat: Double,
        maxLat: Double,
        minLng: Double,
        maxLng: Double,
        immediate: Boolean = false
    ) {
        if (!listOf(minLat, maxLat, minLng, maxLng).all { it.isFinite() }) return

        loadRuinsJob?.cancel()
        loadRuinsJob = viewModelScope.launch {
            if (!immediate) delay(300)

            ruinsRepository.getRuinsByBounds(minLat, maxLat, minLng, maxLng)
                .onSuccess { ruins ->
                    val filtered = ruins.filter {
                        it.latitude in minLat..maxLat && it.longitude in minLng..maxLng
                    }

                    if (filtered.size > 50) {
                        uiState = uiState.copy(visibleRuins = filtered)
                    } else {
                        animationHelper.updateVisibleRuinsSmoothly(
                            currentVisible = uiState.visibleRuins,
                            newRuins = filtered,
                            onUpdate = { updated ->
                                uiState = uiState.copy(visibleRuins = updated)
                            }
                        )
                    }
                }
        }
    }

    fun loadCommentById(id: Int) {
        loadCommentJob?.cancel()
        loadCommentJob = viewModelScope.launch {
            ruinsRepository.getCommentById(id)
                .onSuccess { comments -> uiState = uiState.copy(comments = comments) }
        }
    }

    fun loadQuiz(ruinsId: Int?) {
        uiState = uiState.copy(quizData = null)
        uiState = uiState.copy(wrongAnswers = emptyList())
        uiState = uiState.copy(quizNum = mutableIntStateOf(0))
        uiState = uiState.copy(hintData = MutableList(3) { null })
        if (ruinsId == null) return
        loadQuizJob?.cancel()
        loadQuizJob = viewModelScope.launch {
            quizRepository.getQuizById(ruinsId)
                .onSuccess { quiz ->
                    uiState = uiState.copy(quizData = quiz)
                    updateQuizStatus(QuizStatus.WORKING)
                }
                .onFailure {
                    println("퀴즈 실패")
                }
        }
    }

    fun loadHint(quizId: Int) {
        viewModelScope.launch {
            quizRepository.getQuiz(quizId)
                .onSuccess { hint ->
                    fetchProfile()
                    val newHintData = uiState.hintData.toMutableList()
                    newHintData[uiState.quizNum.value] = hint

                    uiState = uiState.copy(hintData = newHintData)
                    updateHintStatus(HintStatus.HINT)
                }
        }
    }
}