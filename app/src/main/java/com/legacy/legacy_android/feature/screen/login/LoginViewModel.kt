package com.legacy.legacy_android.feature.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.feature.usecase.GoogleLoginUseCase
import com.legacy.legacy_android.feature.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    var loadingState = mutableStateOf(false)
        private set

    val context: Context = application.applicationContext

    fun fetchProfile(){
        viewModelScope.launch {
            userRepository.clearProfile()
            userRepository.fetchProfile()
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
                    fetchProfile()
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
                    fetchProfile()
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
                Nav.setNavStatus(2)
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
