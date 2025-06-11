package com.legacy.legacy_android.feature.network.login

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/kakao/code")
    suspend fun login(@Body code: LoginRequest) : LoginResponse
}