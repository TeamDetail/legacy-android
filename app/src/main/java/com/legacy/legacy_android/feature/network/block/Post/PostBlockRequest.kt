package com.legacy.legacy_android.feature.network.block.Post

data class PostBlockRequest(
    val blockType: String,
    val latitude : Double?,
    val longitude: Double?,
    val userId: Long?,
    val mobileOrWebsite: String
)