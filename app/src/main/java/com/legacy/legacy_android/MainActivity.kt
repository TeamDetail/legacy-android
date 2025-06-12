package com.legacy.legacy_android

import android.content.Context
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.legacy.legacy_android.feature.screen.achieve.AchieveScreen
import com.legacy.legacy_android.feature.screen.achieve.AchieveViewModel
import com.legacy.legacy_android.feature.screen.friend.FriendScreen
import com.legacy.legacy_android.feature.screen.friend.FriendViewModel
import com.legacy.legacy_android.feature.screen.home.HomeScreen
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.feature.screen.login.LoginScreen
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import com.legacy.legacy_android.feature.screen.market.MarketScreen
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.feature.screen.profile.ProfileScreen
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
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
    TRIAL,
    PROFILE,
    FRIEND
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()
        soundId = soundPool.load(this, R.raw.click, 1)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = ScreenNavigate.LOGIN.name) {
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
                composable (route = ScreenNavigate.PROFILE.name){
                    val profileViewModel: ProfileViewModel = hiltViewModel()
                    ProfileScreen(
                        modifier = Modifier,
                        viewModel = profileViewModel,
                        navHostController = navController
                    )
                }
                composable (route = ScreenNavigate.FRIEND.name){
                    val friendViewModel: FriendViewModel = hiltViewModel()
                    FriendScreen(
                        modifier = Modifier,
                        viewModel = friendViewModel,
                        navHostController = navController
                    )
                }
            }
        }
    }
}

