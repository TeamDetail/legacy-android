package com.legacy.legacy_android.feature.screen.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
    private val loginUseCase: LoginUseCase
) : AndroidViewModel(application) {

    var loadingState = mutableStateOf(false)
        private set

    fun loginWithKakao(
        context: Context,
        navHostController: NavHostController,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                loadingState.value = true

                val result = loginUseCase.execute(context)

                if (result.isSuccess) {
                    navigateToHome(navHostController)
                } else {
                    result.error?.let { onFailure(it) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "로그인 처리 중 오류", e)
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
                if (navHostController.currentBackStackEntry != null) {
                    navHostController.navigate(ScreenNavigate.HOME.name) {
                        popUpTo(ScreenNavigate.LOGIN.name) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                } else {
                    println("로그인 안됨")
                }
            } catch (navError: Exception) {
            }
        }
    }
}
