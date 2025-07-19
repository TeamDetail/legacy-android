package com.legacy.legacy_android.feature.network.auth

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

private const val TAG = "KakaoLoginManager"

data class KakaoLoginResult(
    val accessToken: String,
    val refreshToken: String
)

interface KakaoLoginManager {
    suspend fun login(context: Context): Result<KakaoLoginResult>
}

class KakaoLoginManagerImpl @Inject constructor() : KakaoLoginManager {

    override suspend fun login(context: Context): Result<KakaoLoginResult> {
        return try {
            val token = when {
                UserApiClient.instance.isKakaoTalkLoginAvailable(context) -> {
                    loginWithKakaoTalk(context) ?: loginWithKakaoAccount(context)
                }
                else -> loginWithKakaoAccount(context)
            }

            token?.let {
                ensureEmailPermission(context)
                Result.success(KakaoLoginResult(it.accessToken, it.refreshToken))
            } ?: Result.failure(Exception("로그인 토큰을 받지 못했습니다"))

        } catch (e: Exception) {
            Log.e(TAG, "카카오 로그인 실패", e)
            Result.failure(e)
        }
    }

    private suspend fun loginWithKakaoTalk(context: Context): OAuthToken? {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                when {
                    error != null -> {
                        Log.e(TAG, "카카오톡 로그인 실패", error)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            continuation.resume(null)
                        } else {
                            continuation.resume(null) // fallback to account login
                        }
                    }
                    token != null -> {
                        Log.i(TAG, "카카오톡 로그인 성공")
                        continuation.resume(token)
                    }
                    else -> continuation.resume(null)
                }
            }
        }
    }

    private suspend fun loginWithKakaoAccount(context: Context): OAuthToken? {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                when {
                    error != null -> {
                        Log.e(TAG, "카카오 계정 로그인 실패", error)
                        continuation.resume(null)
                    }
                    token != null -> {
                        Log.i(TAG, "카카오 계정 로그인 성공")
                        continuation.resume(token)
                    }
                    else -> continuation.resume(null)
                }
            }
        }
    }

    private suspend fun ensureEmailPermission(context: Context) {
        suspendCancellableCoroutine<Unit> { continuation ->
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e(TAG, "사용자 정보 요청 실패", error)
                    continuation.resume(Unit)
                    return@me
                }

                val kakaoAccount = user?.kakaoAccount
                if (kakaoAccount?.emailNeedsAgreement == true) {
                    UserApiClient.instance.loginWithNewScopes(
                        context,
                        listOf("account_email")
                    ) { _, error ->
                        if (error != null) {
                            Log.e(TAG, "이메일 동의 실패", error)
                        } else {
                            Log.i(TAG, "이메일 동의 성공")
                        }
                        continuation.resume(Unit)
                    }
                } else {
                    continuation.resume(Unit)
                }
            }
        }
    }
}