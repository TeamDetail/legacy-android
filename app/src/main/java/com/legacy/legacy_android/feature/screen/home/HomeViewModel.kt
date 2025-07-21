package com.legacy.legacy_android.feature.screen.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.home.*
import com.legacy.legacy_android.feature.screen.home.helper.RuinsAnimationHelper
import com.legacy.legacy_android.feature.screen.home.model.QuizStatus
import com.legacy.legacy_android.feature.screen.home.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val quizAnswers = mutableListOf<QuizAnswer>()

    fun updateSelectedId(id: Int) {
        uiState = uiState.copy(selectedId = id)
    }

    fun updateMapBounds(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        uiState = uiState.copy(
            mapBounds = MapBounds(minLat, maxLat, minLng, maxLng)
        )
    }

    fun updateQuizStatus(status: QuizStatus){
        uiState = uiState.copy(quizStatus = status)
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

    fun fetchRuinsDetail(id: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)
            ruinsRepository.getRuinsById(id)
                .onSuccess { detail -> uiState = uiState.copy(ruinsDetail = detail) }
                .onFailure { println("유적지 불러오기 실패했어용ㅎㅎ") }
        }
    }

    fun submitQuizAnswers() {
        viewModelScope.launch {
            uiState = uiState.copy(quizStatus = QuizStatus.LOADING)
            quizRepository.submitAnswer(quizAnswers.toList())
                .onSuccess { response ->
                    val wrongIndices = response.data.results
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

    fun loadRuinsMap(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        if (!listOf(minLat, maxLat, minLng, maxLng).all { it.isFinite() }) return
        viewModelScope.launch {
            ruinsRepository.getRuinsByBounds(minLat, maxLat, minLng, maxLng)
                .onSuccess { ruins ->
                    val filtered = ruins.filter {
                        it.latitude in minLat..maxLat && it.longitude in minLng..maxLng
                    }
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

    fun loadQuiz(ruinsId: Int?) {
        viewModelScope.launch {
            quizRepository.getQuizById(ruinsId)
                .onSuccess {
                    quiz -> uiState = uiState.copy(quizData = quiz)
                    updateQuizStatus(QuizStatus.WORKING)
                }
        }
    }
}