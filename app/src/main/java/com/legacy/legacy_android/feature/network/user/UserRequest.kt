package com.legacy.legacy_android.feature.network.user

data class UserResponse(
    val status: Int,
    val message: String,
    val data: UserData
)

data class UserData(
    val userId: Int,
    val nickname: String,
    val level: Int,
    val exp: Int,
    val credit: Int,
    val imageUrl: String,
    val stats: Stats,
    val allBlocks: Int, //모든 블록 수(랭킹)
    val ruinsBlocks: Int, // 유적지 모든 블록 수(랭킹)
    val maxFloor: Int, // 시련 최고 층 수(랭킹)
    val maxScore: Int, // 시련 최고 문명 점수(랭킹)
    val title: Title
)

data class Stats(
    val snowflakeCapacity: Int, // 설화 수용량(스탯)
    val forcedRestock: Int, // 강제 재입고(스탯)
    val creditRecovery: Int,
    val dropCount: Int // 버리기 횟수(스탯)
)

data class Title(
    val name: String,
    val content: String,
    val grade: Int,
    val titleId: Int
)
