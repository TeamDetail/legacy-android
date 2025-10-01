package com.legacy.legacy_android.feature.network.friend

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendService {
    @GET("/friends")
    suspend fun getFriends(): BaseResponse<List<FriendResponse>>
    @GET("/friends/sent")
    suspend fun getSentRequests(): BaseResponse<List<FriendReqResponse>>
    @GET("/friends/requests")
    suspend fun getReceivedRequests(): BaseResponse<List<FriendReqResponse>>
    @GET("/friends/my-code")
    suspend fun getMyCode(): BaseResponse<String>
    @POST("/friends/request")
    suspend fun sendRequest(@Query("friendCode") friendCode: String): BaseResponse<String>

    @DELETE("/friends/sent/{requestId}")
    suspend fun deleteSentRequest(@Path("requestId") requestId: Long): BaseResponse<String>

    @POST("/friends/request/{requestId}/decline")
    suspend fun declineRequest(@Path("requestId") requestId: Long): BaseResponse<String>

    @POST("/friends/request/{requestId}/accept")
    suspend fun acceptRequest(@Path("requestId") requestId: Long): BaseResponse<String>
    @DELETE("/friends/{friendId}")
    suspend fun deleteFriend(@Path("friendId") friendId: Long): BaseResponse<String>
}