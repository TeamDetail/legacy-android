package com.legacy.legacy_android.feature.network.rank

import com.legacy.legacy_android.feature.network.user.Title

data class RankingResponse(
    val nickname: String,
    val level: Int,
    val allBlocks: Int,
    val imageUrl: String,
    val title: Title
)
