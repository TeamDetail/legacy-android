package com.legacy.legacy_android.feature.screen.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.legacy.legacy_android.domain.repository.home.*
import com.legacy.legacy_android.feature.screen.home.helper.RuinsAnimationHelper
import com.legacy.legacy_android.feature.screen.home.model.QuizStatus
import com.legacy.legacy_android.feature.screen.home.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ruinsRepository: RuinsRepository,
    private val blockRepository: BlockRepository,
    private val quizRepository: QuizRepository,
    private val animationHelper: RuinsAnimationHelper,
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    var isMapLoaded by mutableStateOf(false)
        private set

    fun setMapLoaded() {
        isMapLoaded = true
    }

    var cameraPosition by mutableStateOf(
        CameraPosition.fromLatLngZoom(LatLng(35.0, 128.0), 14f)
    )

    private val quizAnswers = mutableListOf<QuizAnswer>()

    fun updateIsMailOpen(isOpen: Boolean) {
        uiState = uiState.copy(isMailOpen = isOpen)
    }

    fun updateSelectedId(id: Int) {
        uiState = uiState.copy(selectedId = id)
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

    fun fetchRuinsDetail(id: Int) {
        if (id == -1) {
            uiState = uiState.copy(ruinsDetail = null)
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)
            ruinsRepository.getRuinsById(id)
                .onSuccess { detail -> uiState = uiState.copy(ruinsDetail = detail) }
            uiState = uiState.copy(loading = false)
        }
    }

    fun submitComment() {
        viewModelScope.launch {
            val currentRuinsDetail = uiState.ruinsDetail
            if (currentRuinsDetail == null) {
                return@launch
            }

            uiState = uiState.copy(commentLoading = true)

            ruinsRepository.postComment(
                currentRuinsDetail.ruinsId,
                uiState.commentRate,
                uiState.commentValue
            )
                .onSuccess {
                    updateIsCommenting(false)
                    uiState = uiState.copy(commentValue = "")
                }
                .onFailure {
                    println("실패")
                }
            uiState = uiState.copy(commentLoading = false)
        }
    }


    fun submitQuizAnswers() {
        viewModelScope.launch {
            uiState = uiState.copy(quizStatus = QuizStatus.LOADING)
            quizRepository.submitAnswer(quizAnswers.toList())
                .onSuccess { response ->
                    val wrongIndices = response!!.results
                        .mapIndexedNotNull { index, result ->
                            if (!result.isCorrect) index else null
                        }
                    uiState = uiState.copy(
                        wrongAnswers = wrongIndices,
                        quizStatus = if (wrongIndices.isEmpty()) QuizStatus.SUCCESS else QuizStatus.RETRY
                    )
                    clearQuizAnswers()
                }
                .onFailure {
                    println("실패했음")
                    println(quizAnswers.toList())
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
        viewModelScope.launch {
            uiState = uiState.copy(createSearchRuins = null)
            uiState = uiState.copy(isSearchLoading = true)
            ruinsRepository.getSearchRuins(name)
                .onSuccess { ruins ->
                    uiState = uiState.copy(createSearchRuins = ruins)
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
        viewModelScope.launch {
            ruinsRepository.getCommentById(id)
                .onSuccess { comments -> uiState = uiState.copy(comments = comments) }
        }
    }

    fun loadQuiz(ruinsId: Int?) {
        viewModelScope.launch {
            quizRepository.getQuizById(ruinsId)
                .onSuccess { quiz ->
                    uiState = uiState.copy(quizData = quiz)
                    updateQuizStatus(QuizStatus.WORKING)
                }
        }
    }

    fun loadHint(quizId: Int) {
        viewModelScope.launch {
            quizRepository.getQuiz(quizId)
                .onSuccess { hint ->
                    uiState = uiState.copy(hintData = hint)
                    updateHintStatus(HintStatus.HINT)
                }
        }
    }
}