package com.legacy.legacy_android.feature.data.core.remote

import android.content.Context
import android.content.Intent
import com.legacy.legacy_android.LegacyApplication
import com.legacy.legacy_android.MainActivity
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.feature.data.user.clearToken
import com.legacy.legacy_android.feature.data.user.getAccToken
import com.legacy.legacy_android.feature.data.user.getRefToken
import com.legacy.legacy_android.feature.data.user.saveAccToken
import com.legacy.legacy_android.feature.data.user.saveRefToken
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient
import com.legacy.legacy_android.feature.network.token.AccTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseInterceptor : Interceptor {
    companion object {
        @Volatile
        private var isRefreshing = false

        @Volatile
        private var isNavigatingToLogin = false

        private val refreshLock = Object()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val context = LegacyApplication.getContext()

        // 1차 요청
        val response = chain.proceed(request)

        if (response.code in 401..402) {
            response.close()

            // 이미 로그인 화면으로 이동 중이면 바로 리턴
            if (isNavigatingToLogin) {
                return createUnauthorizedResponse(request)
            }

            val refToken = getRefToken(context)
            if (refToken.isNullOrEmpty()) {
                navigateToLogin(context)
                return createUnauthorizedResponse(request)
            }

            // 토큰 갱신 동기화
            synchronized(refreshLock) {
                // 다른 스레드가 이미 갱신 중이면 대기
                if (isRefreshing) {
                    // 갱신 완료를 기다림 (최대 5초)
                    try {
                        (refreshLock as Object).wait(5000)
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                    }

                    // 갱신된 토큰으로 재시도
                    val newToken = getAccToken(context)
                    return if (!newToken.isNullOrEmpty()) {
                        val newRequest = request.newBuilder()
                            .removeHeader("Authorization")
                            .header("Authorization", "Bearer $newToken")
                            .build()
                        chain.proceed(newRequest)
                    } else {
                        createUnauthorizedResponse(request)
                    }
                }

                isRefreshing = true
            }

            // 토큰 갱신 시도
            val newAccessToken = runBlocking {
                try {
                    val tokenResponse = RetrofitClient.tokenService.token(AccTokenRequest(refToken))
                    tokenResponse.data?.accessToken?.also { newToken ->
                        saveAccToken(context, newToken)
                        tokenResponse.data.refreshToken.let { saveRefToken(context, it) }
                    }
                } catch (_: Exception) {
                    null
                } finally {
                    synchronized(refreshLock) {
                        isRefreshing = false
                        (refreshLock as Object).notifyAll() // 대기 중인 스레드 깨우기
                    }
                }
            }

            if (!newAccessToken.isNullOrEmpty()) {
                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
                val retryResponse = chain.proceed(newRequest)

                if (retryResponse.code in 401..402) {
                    retryResponse.close()
                    navigateToLogin(context)
                    return createUnauthorizedResponse(request)
                }

                return retryResponse
            } else {
                navigateToLogin(context)
                return createUnauthorizedResponse(request)
            }
        }

        return response
    }

    private fun createUnauthorizedResponse(request: okhttp3.Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("".toResponseBody(null))
            .build()
    }

    private fun navigateToLogin(context: Context) {
        if (isNavigatingToLogin) return

        synchronized(refreshLock) {
            if (isNavigatingToLogin) return
            isNavigatingToLogin = true
        }

        runBlocking { clearToken(context) }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("navigate_to", ScreenNavigate.LOGIN.name)
        }
        context.startActivity(intent)
    }
}