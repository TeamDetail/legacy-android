package com.legacy.legacy_android.feature.data.core.remote

import android.content.Context
import android.content.Intent
import android.util.Log
import com.legacy.legacy_android.LegacyApplication
import com.legacy.legacy_android.MainActivity
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.feature.data.user.clearToken
import com.legacy.legacy_android.feature.data.user.getRefToken
import com.legacy.legacy_android.feature.data.user.saveAccToken
import com.legacy.legacy_android.feature.data.user.saveRefToken
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient
import com.legacy.legacy_android.feature.network.token.AccTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val statusCode = response.code
        val context = LegacyApplication.getContext()

        when (statusCode) {
            400 -> {
                Log.e("API 오류", "잘못된 요청입니다.")
            }

            401, 402 -> {
                Log.d("TokenRefresh", "===== 401/402 에러 발생 =====")

                response.close()

                val refToken = getRefToken(context)
                Log.d("TokenRefresh", "RefreshToken 존재: ${!refToken.isNullOrEmpty()}")
                if (!refToken.isNullOrEmpty()) {
                    Log.d("TokenRefresh", "RefreshToken: ${refToken.take(20)}...")
                }

                if (refToken.isNullOrEmpty()) {
                    Log.e("TokenRefresh", "RefreshToken 없음 -> 로그인 이동")
                    navigateToLogin(context)
                    return createUnauthorizedResponse(request)
                }

                val newTokenResponse = runBlocking {
                    try {
                        Log.d("TokenRefresh", "토큰 갱신 API 호출")
                        val tokenData = AccTokenRequest(refToken)

                        try {
                            val tokenResponse = RetrofitClient.tokenService.token(tokenData)

                            Log.d("TokenRefresh", "토큰 API 응답 받음")
                            Log.d("TokenRefresh", "새 AccessToken 존재: ${tokenResponse.data?.accessToken != null}")
                            Log.d("TokenRefresh", "새 RefreshToken 존재: ${tokenResponse.data?.refreshToken != null}")

                            if (tokenResponse.data?.accessToken != null) {
                                val newAccessToken = tokenResponse.data.accessToken
                                val newRefreshToken = tokenResponse.data.refreshToken

                                Log.d("TokenRefresh", "새 AccessToken: ${newAccessToken.take(20)}...")
                                Log.d("TokenRefresh", "새 RefreshToken: ${newRefreshToken?.take(20)}...")

                                saveAccToken(context, newAccessToken)
                                saveRefToken(context, newRefreshToken)

                                Log.d("TokenRefresh", "새 토큰 저장 완료")
                                newAccessToken
                            } else {
                                Log.e("TokenRefresh", "응답에 토큰 없음")
                                null
                            }
                        } catch (e: Exception) {
                            Log.e("TokenRefresh", "토큰 갱신 실패: ${e.message}", e)
                            null
                        }
                    } catch (e: Exception) {
                        Log.e("TokenRefresh", "토큰 갱신 예외: ${e.message}", e)
                        null
                    }
                }

                if (newTokenResponse != null) {
                    Log.d("TokenRefresh", "토큰 갱신 성공!")

                    // ✅ 재요청 시도하되, 실패해도 괜찮음
                    return try {
                        Log.d("TokenRefresh", "재요청 시도")
                        val newRequest = request.newBuilder()
                            .removeHeader("Authorization")
                            .header("Authorization", "Bearer $newTokenResponse")
                            .build()

                        val retryResponse = chain.proceed(newRequest)
                        Log.d("TokenRefresh", "재요청 성공: ${retryResponse.code}")
                        retryResponse

                    } catch (e: IOException) {
                        Log.w("TokenRefresh", "재요청 취소됨 (원래 요청 타임아웃). 토큰은 갱신되었으므로 다음 요청에서 사용됨: ${e.message}")

                        Response.Builder()
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(200) // 또는 401
                            .message("Token refreshed but original request was cancelled")
                            .body("".toResponseBody(null))
                            .build()

                    } catch (e: Exception) {
                        Log.e("TokenRefresh", "재요청 중 예외: ${e.message}", e)
                        Response.Builder()
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(200)
                            .message("Token refreshed")
                            .body("".toResponseBody(null))
                            .build()
                    }
                } else {
                    Log.e("TokenRefresh", "토큰 갱신 실패 -> 로그인 이동")
                    navigateToLogin(context)
                    return createUnauthorizedResponse(request)
                }
            }

            403 -> {
                Log.e("인터셉터", "권한이 없습니다.")
            }
        }
        return response
    }

    private fun createUnauthorizedResponse(request: okhttp3.Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("".toResponseBody(null))
            .build()
    }

    private fun navigateToLogin(context: Context) {
        Log.d("TokenRefresh", "===== 로그인 화면으로 이동 =====")
        runBlocking {
            clearToken(context)
            Log.d("TokenRefresh", "토큰 클리어 완료")
        }
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", ScreenNavigate.LOGIN.name)
        }
        context.startActivity(intent)
        Log.d("TokenRefresh", "MainActivity 시작")
    }
}