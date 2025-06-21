package com.legacy.legacy_android.feature.network.user

import com.google.gson.annotations.SerializedName

data class GetMeResponse(
    val status: Int,
    val message: String?,
    val data: UserData?
)

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
    @SerializedName("storeRestock")
    val forcedRestock: Int,
    @SerializedName("creditCollect")
    val creditRecovery: Int,
    val dropCount: Int
)

data class Title(
    val name: String,
    val content: String,
    @SerializedName("styleId")
    val grade: Int
)
