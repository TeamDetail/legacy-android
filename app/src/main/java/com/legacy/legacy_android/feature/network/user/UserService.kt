package com.legacy.legacy_android.feature.network.user

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET

interface UserService {
    @GET("user/me")
    suspend fun profile(): BaseResponse<UserResponse>
}