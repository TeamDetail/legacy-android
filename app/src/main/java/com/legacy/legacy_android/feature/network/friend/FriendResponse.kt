package com.legacy.legacy_android.feature.network.friend

data class FriendResponse(
    val userId: Long,
    val nickname: String,
    val profileImage: String,
    val friendCode: String,
    val isKakaoFriend: Boolean,
    val isMutualFriend: Boolean,
    val level: Int
)

data class SearchFriendResponse(
    val userId: Long,
    val nickname: String,
    val profileImage: String,
    val level: Int,
    val friendCode: String,
    val isAlreadyFriend: Boolean
)