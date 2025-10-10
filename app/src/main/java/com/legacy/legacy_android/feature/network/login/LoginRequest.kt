package com.legacy.legacy_android.feature.network.login

data class KakaoLoginRequest(
    val accessToken: String,
    val refreshToken: String
)

data class AppleLoginRequest(
    val code: String,
    val name: String
)

data class AppleAccessTokenRequest(
    val idToken: String,
    val name: String,
)

data class GoogleLoginRequest(
    val idToken: String
)


