package com.legacy.legacy_android.feature.screen.ranking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.rank.RankingRepository
import com.legacy.legacy_android.feature.screen.ranking.model.RankingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankingRepository: RankingRepository
) : ViewModel() {

    var uiState by mutableStateOf(RankingUiState())
        private set

    fun fetchRanking() {
        viewModelScope.launch {
            val result = if (uiState.rankingStatus == 0) {
                rankingRepository.fetchExploreRanking(if (uiState.friendStatus == 0) "ALL" else "FRIEND")
            } else {
                rankingRepository.fetchLevelRanking(if (uiState.friendStatus == 0) "ALL" else "FRIEND")
            }

            result.onSuccess { data ->
                if (data != null) {
                    uiState = uiState.copy(rankingData = data)
                }
            }
        }
    }

    fun changeRankingStatus(status: Int) {
        uiState = uiState.copy(rankingStatus = status)
        fetchRanking()
    }

    fun changeFriendStatus(status: Int) {
        uiState = uiState.copy(friendStatus = status)
        fetchRanking()
    }

}
