package com.legacy.legacy_android.feature.network.friend

data class FriendReqResponse(
    val requestId: Long,
    val senderId: Long,
    val receiverId: Long,
    val senderNickname: String,
    val senderProfileImage: String,
    val senderLevel: Int,
    val receiverNickname: String,
    val receiverProfileImage: String,
    val receiverLevel: Int,
    val status: String,
    val createdAt: String,
    val senderStyleId: Int,
    val senderStyleName: String,
    val receiverStyleId: Int,
    val receiverStyleName: String
)
