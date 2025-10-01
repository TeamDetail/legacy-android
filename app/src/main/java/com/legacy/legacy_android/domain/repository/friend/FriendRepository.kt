package com.legacy.legacy_android.domain.repository.friend

import android.util.Log
import com.legacy.legacy_android.feature.network.friend.FriendReqResponse
import com.legacy.legacy_android.feature.network.friend.FriendResponse
import com.legacy.legacy_android.feature.network.friend.FriendService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRepository @Inject constructor(
    private val friendService: FriendService,
) {
    suspend fun getFriends(): Result<List<FriendResponse>> {
        return try {
            val response = friendService.getFriends()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("친구 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSentRequests(): Result<List<FriendReqResponse>> {
        return try {
            val response = friendService.getSentRequests()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("보낸 친구 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReceivedRequests(): Result<List<FriendReqResponse>> {
        return try {
            val response = friendService.getReceivedRequests()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("받은 요청 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Log.e("FriendRepository", "예외 발생: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun getMyCode(): Result<String> {
        return try {
            val response = friendService.getMyCode()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("코드 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendRequest(code: String): Result<Unit> {
        return try {
            friendService.sendRequest(code)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FriendRepository", "친구 요청 실패: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun deleteSentRequest(requestId: Long): Result<Unit> {
        return try {
            friendService.deleteSentRequest(requestId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FriendRepository", "보낸 요청 삭제 실패: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun declineRequest(requestId: Long): Result<Unit> {
        return try {
            friendService.declineRequest(requestId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FriendRepository", "요청 거절 실패: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun acceptRequest(requestId: Long): Result<Unit> {
        return try {
            friendService.acceptRequest(requestId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FriendRepository", "요청 수락 실패: ${e.message}", e)
            Result.failure(e)
        }
    }
}