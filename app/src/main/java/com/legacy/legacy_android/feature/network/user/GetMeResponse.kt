package com.legacy.legacy_android.feature.network.user

data class UserData(
    val userId: Long,
    val nickname: String,
    val level: Int,
    val exp: Int,
    val credit: Int,
    val imageUrl: String,
    val stats: Stats,
    val allBlocks: Int,
    val ruinsBlocks: Int,
    val maxFloor: Int,
    val maxScore: Int,
    val title: Title
)

data class Stats(
    val snowflakeCapacity: Int,
    val storeRestock: Int,
    val dropCount: Int,
    val creditCollect: Int
)

data class Title(
    val name: String,
    val content: String,
    val styleId: Int
)
