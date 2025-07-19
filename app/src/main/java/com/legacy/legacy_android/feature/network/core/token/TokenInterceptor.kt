package com.legacy.legacy_android.feature.network.core.token

import android.content.Context
import com.legacy.legacy_android.feature.data.user.getAccToken
import com.legacy.legacy_android.feature.data.user.saveAccToken
import com.legacy.legacy_android.feature.data.user.saveRefToken
import com.legacy.legacy_android.feature.network.token.AccTokenRequest
import com.legacy.legacy_android.feature.network.token.TokenService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val context: Context,
    private val tokenService: TokenService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 액세스 토큰을 가져온다.
        val accessToken = getAccToken(context)
            ?: return chain.proceed(chain.request())

        // 토큰을 헤더에 자동으로 집어넣어준다.
        val tokenAddedRequest = chain.request().newBuilder()
            .addHeader("authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(tokenAddedRequest)

        // 응답이 401 Unauthorization 이라면
        if (response.code == 401) {
            // 리프레시 토큰을 가져와서, 갱신 요청을 한다.
            var refreshedAccessToken: String
            var refreshedRefreshToken: String
            runBlocking {
                val refreshTokenRequest = AccTokenRequest(accessToken)
                val refreshTokenResponse =
                    RetrofitInstance.apiService.token(refreshTokenRequest)

                // 갱신 결과를 로컬스토리지(여기선 SharedPreferences)에 저장한다.
                refreshedAccessToken = refreshTokenResponse.data?.accessToken ?: "ad"
                refreshedRefreshToken = refreshTokenResponse.data?.refreshToken ?: "ad"
                saveAccToken(context,
                    refreshedAccessToken
                )
                saveRefToken(
                    context,
                    refreshedRefreshToken
                )
            }

            val refreshedRequest = chain.request().newBuilder()
                .addHeader("authorization", "Bearer $refreshedAccessToken")
                .build()
            return chain.proceed(refreshedRequest)
        }

        return response
    }
}