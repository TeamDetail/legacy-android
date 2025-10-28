package com.legacy.legacy_android.feature.network.block.post

data class PostBlockResponse (
    val blockId: Int,
    val ruinsId: Int,
    val blockType: String,
    val latitude: Int,
    val longitude: Int
)