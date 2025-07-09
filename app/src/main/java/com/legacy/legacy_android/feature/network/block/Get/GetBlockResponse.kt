package com.legacy.legacy_android.feature.network.block.Get

data class GetBlockResponse (
    val blockId: Int,
    val blockName: String,
    val blockType: String,
    val latitude: Double,
    val longitude: Double
)