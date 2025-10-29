package com.legacy.legacy_android.feature.network.login

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/kakao/accessToken")
    suspend fun login(@Body code: KakaoLoginRequest) : BaseResponse<TokenData>

    @POST("/google/android")
    suspend fun googleLogin(@Body code: GoogleLoginRequest) : BaseResponse<TokenData>
}