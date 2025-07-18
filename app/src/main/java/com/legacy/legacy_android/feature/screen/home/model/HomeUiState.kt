package com.legacy.legacy_android.feature.screen.home.model

import com.legacy.legacy_android.feature.network.block.Get.GetBlockResponse
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import com.legacy.legacy_android.feature.network.ruinsId.RuinsIdResponse

enum class HintStatus { NO, CREDIT, HINT }
enum class QuizStatus { NONE, WORKING, LOADING, SUCCESS, RETRY }
data class MapBounds(
    val minLat: Double = 0.0,
    val maxLat: Double = 0.0,
    val minLng: Double = 0.0,
    val maxLng: Double = 0.0
)

data class HomeUiState(
    val loading: Boolean = false,
    val selectedId: Int = -1,
    val hintStatus: HintStatus = HintStatus.NO,
    val quizStatus: QuizStatus = QuizStatus.NONE,
    val visibleRuins: List<RuinsMapResponse> = emptyList(),
    val ruinsDetail: RuinsIdResponse? = null,
    val quizData: List<GetQuizResponse>? = null,
    val blocks: List<GetBlockResponse> = emptyList(),
    val wrongAnswers: List<Int> = emptyList(),
    val mapBounds: MapBounds = MapBounds()
)
