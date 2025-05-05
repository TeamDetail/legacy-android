package com.legacy.legacy_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.legacy.legacy_android.feature.screen.home.HomeScreen
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.feature.screen.login.LoginScreen
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

enum class ScreenNavigate {
    SPLASH,
    LOGIN,
    HOME
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = ScreenNavigate.HOME.name) {
                composable(route = ScreenNavigate.LOGIN.name) {
                    val loginViewModel: LoginViewModel = hiltViewModel()
                    LoginScreen(
                        modifier = Modifier,
                        viewModel = loginViewModel,
                        onMoveScreen = { destination ->
                            navController.navigate(destination)
                        }
                    )
                }
                    composable(route = ScreenNavigate.HOME.name) {
                        val homeViewModel: HomeViewModel = hiltViewModel()
                        HomeScreen(
                            modifier = Modifier,
                            viewModel = homeViewModel,
                            onMoveScreen = { destination ->
                                navController.navigate(destination)
                            }
                        )
                    }
            }
        }
    }
}

