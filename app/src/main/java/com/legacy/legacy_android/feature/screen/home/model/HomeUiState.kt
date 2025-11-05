package com.legacy.legacy_android.feature.screen.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.feature.network.block.get.GetBlockResponse
import com.legacy.legacy_android.feature.network.quiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsCommentResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse

enum class HintStatus { NO, CREDIT, HINT }
enum class QuizStatus { NONE, WORKING, LOADING, SUCCESS, RETRY, SHARE }
data class MapBounds(
    val minLat: Double = 0.0,
    val maxLat: Double = 0.0,
    val minLng: Double = 0.0,
    val maxLng: Double = 0.0
)

data class HomeUiState(
    val loading: Boolean = false,
    val selectedId: List<Int> = emptyList(),
    val hintStatus: HintStatus = HintStatus.NO,
    val quizStatus: QuizStatus = QuizStatus.NONE,
    val visibleRuins: List<RuinsMapResponse> = emptyList(),
    val ruinsDetail: List<RuinsIdResponse>? = null,
    val selectedRuinsDetail: RuinsIdResponse? = null,
    val quizData: List<GetQuizResponse>? = null,
    val blocks: List<GetBlockResponse> = emptyList(),
    val wrongAnswers: List<Int> = emptyList(),
    val mapBounds: MapBounds = MapBounds(),
    val isSearchRuinOpen: Boolean = false,
    val searchRuinValue: MutableState<String> = mutableStateOf(""),
    val createSearchRuins: List<RuinsIdResponse>? = null,
    val isSearchLoading: Boolean = false,
    val comments: List<RuinsCommentResponse>? = null,
    val commentValue: String = "",
    val isCommenting: Boolean = false,
    val commentLoading: Boolean = false,
    val isCommentModalOpen: Boolean = false,
    val commentRate: Int = 0,
    val isMailOpen: Boolean = false,
    val isCheckOpen: Boolean = false,
    val isInfoOpen: Boolean = false,
    val hintData: MutableList<String?> = MutableList(3) { null },
    val quizNum: MutableState<Int> = mutableIntStateOf(0),
)
