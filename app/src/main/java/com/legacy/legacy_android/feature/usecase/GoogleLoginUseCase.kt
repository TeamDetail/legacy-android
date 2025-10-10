package com.legacy.legacy_android.feature.usecase

import android.app.Activity
import android.util.Log
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.legacy.legacy_android.domain.repository.GoogleSignInRepository
import com.legacy.legacy_android.domain.repository.TokenRepository
import com.legacy.legacy_android.feature.network.login.GoogleLoginRequest
import com.legacy.legacy_android.feature.network.login.LoginService
import javax.inject.Inject


class GoogleLoginUseCase @Inject constructor(
    private val googleSignInRepository: GoogleSignInRepository,
    private val loginService: LoginService,
    private val tokenRepository: TokenRepository,
    private val dataStoreManager: DataStoreManager
) {
    suspend fun execute(activity: Activity): LoginResult {
        return try {
            val googleResult = googleSignInRepository.signIn(activity)
            if (googleResult.isFailure) {
                return LoginResult(false, googleResult.exceptionOrNull())
            }

            val credential = googleResult.getOrThrow()

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken

            dataStoreManager.saveGoogleIdToken(idToken)

            // 백엔드 로그인 API 호출
            val request = GoogleLoginRequest(idToken = idToken)
            val response = loginService.googleLogin(request)

            // 토큰 저장
            tokenRepository.saveTokens(
                response.data.accessToken,
                response.data.refreshToken
            )

            Log.d("GoogleLoginUseCase", "구글 로그인 성공")
            LoginResult(true)
        } catch (e: Exception) {
            Log.e("GoogleLoginUseCase", "구글 로그인 실패", e)
            LoginResult(false, e)
        }
    }
}