package com.legacy.legacy_android.feature.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.legacy.legacy_android.BuildConfig
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.feature.network.login.AppleAccessTokenRequest
import com.legacy.legacy_android.feature.network.login.AppleLoginRequest
import com.legacy.legacy_android.feature.network.login.AppleSignInDataSource
import com.legacy.legacy_android.feature.network.login.LoginResponse
import com.legacy.legacy_android.feature.network.login.LoginService
import com.legacy.legacy_android.feature.usecase.GoogleLoginUseCase
import com.legacy.legacy_android.feature.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val appleSignInDataSource: AppleSignInDataSource,
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val loginService: LoginService
) : AndroidViewModel(application) {

    var loadingState = mutableStateOf(false)
        private set

    val context = application.applicationContext

    private val _loginResult = MutableStateFlow<Result<LoginResponse>?>(null)

    fun startAppleLogin(activity: Activity) {
        val clientId = BuildConfig.APPLE_CLIENT_ID
        val redirectUri = BuildConfig.APPLE_REDIRECT_URL

        val authUrl = Uri.Builder()
            .scheme("https")
            .authority("appleid.apple.com")
            .appendPath("auth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("response_type", "code id_token")
            .appendQueryParameter("scope", "name email")
            .appendQueryParameter("response_mode", "form_post")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, authUrl)
        val chooser = Intent.createChooser(intent, "Sign in with Apple")
        activity.startActivity(chooser)
    }


    fun loginWithApple(idToken: String, userName: String) {
        if (loadingState.value) return
        loadingState.value = true

        viewModelScope.launch {
            try {
                val codeRequest = AppleLoginRequest(code = idToken, name = userName)
                val tokenResponse = loginService.appleLogin(codeRequest)
                val accessToken = tokenResponse.data.accessToken

                val loginResponse = loginService.appleAccessToken(
                    AppleAccessTokenRequest(idToken = accessToken, name = userName)
                )

                _loginResult.value = Result.success(loginResponse)

            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            } finally {
                loadingState.value = false
            }
        }
    }


    fun loginWithGoogle(
        activity: Activity,
        navHostController: NavHostController,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                loadingState.value = true
                val result = googleLoginUseCase.execute(activity)

                if (result.isSuccess) {
                    Log.d(TAG, "Google 로그인 성공")
                    navigateToHome(navHostController)
                } else {
                    Log.d(TAG, "Google 로그인 실패")
                    result.error?.let { onFailure(it) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Google 로그인 처리 중 오류", e)
                onFailure(e)
            } finally {
                loadingState.value = false
            }
        }
    }

    fun loginWithKakao(
        context: Context,
        navHostController: NavHostController,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                loadingState.value = true
                val result = kakaoLoginUseCase.execute(context)
                if (result.isSuccess) {
                    navigateToHome(navHostController)
                } else {
                    result.error?.let { onFailure(it) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "카카오 로그인 처리 중 오류", e)
                onFailure(e)
            } finally {
                loadingState.value = false
            }
        }
    }

    private suspend fun navigateToHome(navHostController: NavHostController) {
        withContext(Dispatchers.Main) {
            try {
                delay(100)
                navHostController.navigate(ScreenNavigate.HOME.name) {
                    popUpTo(ScreenNavigate.LOGIN.name) {
                        inclusive = true
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } catch (navError: Exception) {
                Log.e(TAG, "로그인 화면 전환 중 오류", navError)
            }
        }
    }
}