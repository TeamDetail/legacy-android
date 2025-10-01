package com.legacy.legacy_android.feature.network.block.get

data class GetBlockResponse (
    val blockId: Int,
    val blockType: String,
    val latitude: Double,
    val longitude: Double
)