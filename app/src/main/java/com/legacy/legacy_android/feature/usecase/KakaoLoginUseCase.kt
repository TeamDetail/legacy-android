package com.legacy.legacy_android.feature.usecase

import android.content.Context
import android.util.Log
import com.legacy.legacy_android.domain.repository.TokenRepository
import com.legacy.legacy_android.domain.repository.friend.FriendRepository
import com.legacy.legacy_android.feature.network.auth.KakaoLoginManager
import com.legacy.legacy_android.feature.network.login.KakaoLoginRequest
import com.legacy.legacy_android.feature.network.login.LoginService
import javax.inject.Inject

data class LoginResult(
    val isSuccess: Boolean,
    val error: Throwable? = null
)

class KakaoLoginUseCase @Inject constructor(
    private val kakaoLoginManager: KakaoLoginManager,
    private val loginService: LoginService,
    private val tokenRepository: TokenRepository,
    private val dataStoreManager: DataStoreManager,
    private val friendRepository: FriendRepository
) {
    suspend fun execute(context: Context): LoginResult {
        return try {
            val kakaoResult = kakaoLoginManager.login(context)
            if (kakaoResult.isFailure) {
                return LoginResult(false, kakaoResult.exceptionOrNull())
            }

            val kakaoLogin = kakaoResult.getOrThrow()
            dataStoreManager.saveKakaoToken(kakaoLogin.accessToken)

            val request = KakaoLoginRequest(
                accessToken = kakaoLogin.accessToken,
                refreshToken = kakaoLogin.refreshToken
            )
            val response = loginService.login(request)
            tokenRepository.saveTokens(
                response.data.accessToken,
                response.data.refreshToken
            )

            try {
                friendRepository.friendKakao(kakaoLogin.accessToken)
                Log.d("LoginUseCase", "카카오 친구 추가 성공")
            } catch (e: Exception) {
                Log.e("LoginUseCase", "카카오 친구 추가 실패", e)
            }

            LoginResult(true)
        } catch (e: Exception) {
            Log.e("LoginUseCase", "로그인 실패", e)
            LoginResult(false, e)
        }
    }
}

