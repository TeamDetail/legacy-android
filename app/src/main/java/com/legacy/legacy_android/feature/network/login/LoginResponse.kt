package com.legacy.legacy_android.feature.network.login

data class LoginResponse(
    val data: TokenData,
    val status: Int
)

data class TokenData(
    val accessToken: String,
    val refreshToken: String
)