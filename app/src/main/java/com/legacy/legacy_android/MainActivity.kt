package com.legacy.legacy_android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.legacy.legacy_android.feature.screen.achieve.AchieveScreen
import com.legacy.legacy_android.feature.screen.achieve.AchieveViewModel
import com.legacy.legacy_android.feature.screen.home.HomeScreen
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.feature.screen.login.LoginScreen
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import com.legacy.legacy_android.feature.screen.market.MarketScreen
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.feature.screen.ranking.RankingScreen
import com.legacy.legacy_android.feature.screen.ranking.RankingViewModel
import com.legacy.legacy_android.feature.screen.trial.TrialScreen
import com.legacy.legacy_android.feature.screen.trial.TrialViewModel
import dagger.hilt.android.AndroidEntryPoint

enum class ScreenNavigate {
    LOGIN,
    HOME,
    MARKET,
    RANKING,
    ACHIEVE,
    TRIAL
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = ScreenNavigate.MARKET.name) {
                composable(route = ScreenNavigate.LOGIN.name) {
                    val loginViewModel: LoginViewModel = hiltViewModel()
                    LoginScreen(
                        modifier = Modifier,
                        viewModel = loginViewModel,
                        navHostController = navController
                    )
                }
                composable(route = ScreenNavigate.HOME.name) {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(
                        modifier = Modifier,
                        viewModel = homeViewModel,
                        navHostController = navController
                    )
                }
                composable(route = ScreenNavigate.MARKET.name) {
                    val marketViewModel: MarketViewModel = hiltViewModel()
                    MarketScreen(
                        modifier = Modifier,
                        viewModel = marketViewModel,
                        navHostController = navController
                    )
                }
                composable (route = ScreenNavigate.ACHIEVE.name){
                    val achieveViewModel: AchieveViewModel = hiltViewModel()
                    AchieveScreen(
                        modifier = Modifier,
                        viewModel = achieveViewModel,
                        navHostController = navController
                    )
                }
                composable (route = ScreenNavigate.TRIAL.name){
                    val trialViewModel: TrialViewModel = hiltViewModel()
                    TrialScreen(
                        modifier = Modifier,
                        viewModel = trialViewModel,
                        navHostController = navController
                    )
                }
                composable (route = ScreenNavigate.RANKING.name){
                    val rankingViewModel: RankingViewModel = hiltViewModel()
                    RankingScreen(
                        modifier = Modifier,
                        viewModel = rankingViewModel,
                        navHostController = navController
                    )
                }
            }
        }
    }
}

