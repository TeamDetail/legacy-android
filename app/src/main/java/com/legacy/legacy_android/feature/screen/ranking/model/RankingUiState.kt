package com.legacy.legacy_android.feature.screen.ranking.model

import com.legacy.legacy_android.feature.network.rank.RankingResponse

data class RankingUiState(
    val rankingData: List<RankingResponse>? = null,
    val rankingStatus: Int = 0
)
