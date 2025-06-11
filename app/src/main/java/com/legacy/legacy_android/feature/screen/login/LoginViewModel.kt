package com.legacy.legacy_android.feature.screen.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.feature.network.login.LoginRequest
import com.legacy.legacy_android.feature.network.login.LoginService
import com.legacy.legacy_android.feature.network.user.saveUser.saveAccToken
import com.legacy.legacy_android.feature.network.user.saveUser.saveRefToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val loginService: LoginService
) : AndroidViewModel(application) {

    fun loginWithKakao(
        context: Context,
        navHostController: NavHostController,
        onFailure: (Throwable) -> Unit
    ) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                Log.d(TAG, "토큰: $token")
                onFailure(error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken} ${token.refreshToken}")
                // 백엔드 로그인 호출
                callBackendLogin(
                    kakaoAccessToken = token.accessToken,
                    kakaoRefreshToken = token.refreshToken,
                    onBackendLoginSuccess = { Log.i("성공", "벡엔드") },
                    onFailure = onFailure,
                    context = context,
                    navHostController = navHostController
                )
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    callBackendLogin(
                        kakaoAccessToken = token.accessToken,
                        kakaoRefreshToken = token.refreshToken,
                        onBackendLoginSuccess = { Log.i("성공", "벡엔드") },
                        onFailure = onFailure,
                        context = context,
                        navHostController = navHostController
                    )
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun callBackendLogin(
        kakaoAccessToken: String,
        kakaoRefreshToken: String,
        onBackendLoginSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
        context: Context,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(accessToken = kakaoAccessToken, refreshToken = kakaoRefreshToken)
                val response = loginService.login(request)
                Log.i(TAG, "백엔드 로그인 성공: AccessToken = ${response.data.accessToken}, RefreshToken = $response.data.accessToken}")
                onBackendLoginSuccess()
                saveAccToken(context, response.data.accessToken)
                saveRefToken(context, response.data.accessToken)
                navHostController.navigate(ScreenNavigate.HOME.name)
            } catch (e: Exception) {
                Log.e(TAG, "백엔드 로그인 실패", e)
                onFailure(e)
            }
        }
    }
}
