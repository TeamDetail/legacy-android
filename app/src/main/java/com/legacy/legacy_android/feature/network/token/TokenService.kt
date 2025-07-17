package com.legacy.legacy_android.feature.network.token

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("/auth/refresh")
    suspend fun token(@Body refresh: AccTokenRequest) : BaseResponse<AccTokenResponse>
}