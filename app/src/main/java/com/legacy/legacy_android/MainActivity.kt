package com.legacy.legacy_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.legacy.legacy_android.feature.screen.home.HomeScreen
import com.legacy.legacy_android.feature.screen.login.LoginScreen

enum class ScreenNavigate {
    SPLASH,
    LOGIN,
    HOME
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = ScreenNavigate.LOGIN.name) {
                composable(route = ScreenNavigate.HOME.name) {
                    LoginScreen(
                        modifier = Modifier,
                        onMoveScreen = { destination ->
                            navController.navigate(destination)
                        }
                    )
                    HomeScreen(
                        modifier = Modifier,
                        onMoveScreen = {
                            destination -> navController.navigate(destination)
                        }
                    )
                }
            }
        }
    }
}

