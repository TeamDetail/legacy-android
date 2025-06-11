package com.legacy.legacy_android.feature.screen.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
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
        onSuccess: (OAuthToken) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                onFailure(error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken} ${token.refreshToken}")
                onSuccess(token)
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
                    onSuccess(token)
                    callBackendLogin(kakaoAccessToken = token.accessToken, kakaoRefreshToken = token.refreshToken,
                        onSuccess = onSuccess, onFailure = onFailure, token)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
    private fun callBackendLogin(
        kakaoAccessToken: String,
        kakaoRefreshToken: String,
        onSuccess: (OAuthToken) -> Unit,
        onFailure: (Throwable) -> Unit,
        token: OAuthToken
    ) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(accessToken = kakaoAccessToken, refreshToken = kakaoRefreshToken)
                val response = loginService.login(request)

                Log.i(TAG, "백엔드 로그인 성공: AccessToken = ${response.accessToken}, RefreshToken = ${response.refreshToken}")

                saveAccToken(
                    context = getApplication<Application>().applicationContext,
                    token = response.accessToken
                )
                saveRefToken(
                    context = getApplication<Application>().applicationContext,
                    token = response.refreshToken
                )
                onSuccess(token)

            } catch (e: Exception) {
                Log.e(TAG, "백엔드 로그인 실패", e)
                onFailure(e)
            }
        }
    }
}

