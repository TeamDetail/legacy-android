package com.legacy.legacy_android.feature.screen.friend.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.feature.network.friend.FriendReqResponse
import com.legacy.legacy_android.feature.network.friend.FriendResponse

data class FriendUiState (
    val friendList: List<FriendResponse> = emptyList(),
    val friendStatus: Int = 0,
    val sentRequestList: List<FriendReqResponse> = emptyList(),
    val receiveRequestList: List<FriendReqResponse> = emptyList(),
    val myCode: String = "",
    val friendCode: String = "",
    val searchFriend: MutableState<String> = mutableStateOf(""),
    val requestError: Boolean? = null,
    val setDeleteFriend: Boolean = false,
    val currentFriend: FriendResponse? = null
)