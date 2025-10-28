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

            Log.d("GoogleLogin", "ID Token 획득: ${idToken.take(20)}...")

            dataStoreManager.saveGoogleIdToken(idToken)

            // 백엔드 로그인 API 호출
            val request = GoogleLoginRequest(idToken = idToken)
            Log.d("GoogleLogin", "서버로 요청 전송 중...")

            val response = loginService.googleLogin(request)

            Log.d("GoogleLogin", "서버 응답 성공: ${response.data}")

            // 토큰 저장
            tokenRepository.saveTokens(
                response.data.accessToken,
                response.data.refreshToken
            )

            Log.d("GoogleLoginUseCase", "구글 로그인 성공")
            LoginResult(true)
        } catch (e: retrofit2.HttpException) {
            // HTTP 에러 상세 로깅
            Log.e("GoogleLoginUseCase", "HTTP ${e.code()} 에러 발생")
            try {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("GoogleLoginUseCase", "서버 에러 응답: $errorBody")
            } catch (ex: Exception) {
                Log.e("GoogleLoginUseCase", "에러 본문 읽기 실패", ex)
            }
            LoginResult(false, e)
        } catch (e: Exception) {
            Log.e("GoogleLoginUseCase", "구글 로그인 실패", e)
            LoginResult(false, e)
        }
    }
}