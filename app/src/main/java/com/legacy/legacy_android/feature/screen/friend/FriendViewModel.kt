package com.legacy.legacy_android.feature.screen.friend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.friend.FriendRepository
import com.legacy.legacy_android.feature.network.friend.FriendResponse
import com.legacy.legacy_android.feature.screen.friend.model.FriendUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendRepository: FriendRepository
) : ViewModel() {

    var uiState by mutableStateOf(FriendUiState())
        private set

    fun fetchFriendList() {
        viewModelScope.launch {
            val result = friendRepository.getFriends()
            result.onSuccess { friendList ->
                uiState = uiState.copy(friendList = friendList)
            }.onFailure {
                uiState = uiState.copy(friendList = emptyList())
            }
        }
    }

    fun sendFriendRequest() {
        viewModelScope.launch {
            if (uiState.friendCode.isBlank()) return@launch
            val result = friendRepository.sendRequest(uiState.friendCode)
            result.onSuccess {
                uiState = uiState.copy(
                    requestError = false,
                    friendCode = ""
                )
                fetchSentRequestList()
                delay(3000)
                uiState = uiState.copy(requestError = null)
            }.onFailure {
                uiState = uiState.copy(requestError = true)
                delay(3000)
                uiState = uiState.copy(requestError = null)
            }
        }
    }

    fun deleteSentRequest(requestId: Long) {
        viewModelScope.launch {
            val result = friendRepository.deleteSentRequest(requestId)
            result.onSuccess {
                fetchSentRequestList()
            }.onFailure {
            }
        }
    }

    fun acceptRequest(requestId: Long) {
        viewModelScope.launch {
            val result = friendRepository.acceptRequest(requestId)
            result.onSuccess {
                fetchRequestList()
                fetchFriendList()
            }.onFailure {
            }
        }
    }

    fun declineRequest(requestId: Long) {
        viewModelScope.launch {
            val result = friendRepository.declineRequest(requestId)
            result.onSuccess {
                fetchRequestList()
            }.onFailure {
            }
        }
    }

    fun changeFriendCode(code: String) {
        uiState = uiState.copy(friendCode = code)
    }

    fun changeFriendStatus(status: Int) {
        uiState = uiState.copy(friendStatus = status)
    }

    fun fetchSentRequestList() {
        viewModelScope.launch {
            val result = friendRepository.getSentRequests()
            result.onSuccess { sentRequestList ->
                uiState = uiState.copy(sentRequestList = sentRequestList)
            }.onFailure {
                uiState = uiState.copy(sentRequestList = emptyList())
            }
        }
    }

    fun fetchMyCode() {
        viewModelScope.launch {
            val result = friendRepository.getMyCode()
            result.onSuccess { myCode ->
                uiState = uiState.copy(myCode = myCode)
            }.onFailure {
                uiState = uiState.copy(myCode = "")
            }
        }
    }

    fun fetchRequestList() {
        viewModelScope.launch {
            val result = friendRepository.getReceivedRequests()
            result.onSuccess { requestList ->
                uiState = uiState.copy(receiveRequestList = requestList)
            }.onFailure {
                uiState = uiState.copy(receiveRequestList = emptyList())
            }
        }
    }

    fun deleteFriend(friendId: Long) {
        viewModelScope.launch {
            val result = friendRepository.deleteFriend(friendId)
            result.onSuccess {
                fetchFriendList()
            }.onFailure {
            }
        }
    }

    fun setDeleteFriend(value: Boolean) {
        uiState = uiState.copy(setDeleteFriend = value)
    }

    fun setCurrentFriend(value: FriendResponse) {
        uiState = uiState.copy(currentFriend = value)
    }

    fun searchFriend() {
        viewModelScope.launch {
            val result = friendRepository.searchFriend(uiState.searchFriend.value)
            result.onSuccess { friendList ->
                uiState = uiState.copy(searchFriendList = friendList)
            }
        }
    }
}