package com.legacy.legacy_android.feature.usecase

import android.content.Context
import android.util.Log
import com.legacy.legacy_android.domain.repository.TokenRepository
import com.legacy.legacy_android.feature.network.auth.KakaoLoginManager
import com.legacy.legacy_android.feature.network.login.LoginRequest
import com.legacy.legacy_android.feature.network.login.LoginService
import javax.inject.Inject

private const val TAG = "LoginUseCase"

data class LoginResult(
    val isSuccess: Boolean,
    val error: Throwable? = null
)

class LoginUseCase @Inject constructor(
    private val kakaoLoginManager: KakaoLoginManager,
    private val loginService: LoginService,
    private val tokenRepository: TokenRepository
) {

    suspend fun execute(context: Context): LoginResult {
        return try {
            val kakaoResult = kakaoLoginManager.login(context)
            if (kakaoResult.isFailure) {
                return LoginResult(false, kakaoResult.exceptionOrNull())
            }

            val kakaoLogin = kakaoResult.getOrThrow()

            val request = LoginRequest(
                accessToken = kakaoLogin.accessToken,
                refreshToken = kakaoLogin.refreshToken
            )

            val response = loginService.login(request)
            Log.i(TAG, "백엔드 로그인 성공, ${response.data.accessToken}")

            tokenRepository.saveTokens(
                response.data.accessToken,
                response.data.refreshToken
            )

            LoginResult(true)

        } catch (e: Exception) {
            Log.e(TAG, "로그인 실패", e)
            LoginResult(false, e)
        }
    }
}
