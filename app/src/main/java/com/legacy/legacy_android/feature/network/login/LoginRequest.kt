package com.legacy.legacy_android.feature.network.login

data class KakaoLoginRequest(
    val accessToken: String,
    val refreshToken: String
)

data class GoogleLoginRequest(
    val idToken: String
)


