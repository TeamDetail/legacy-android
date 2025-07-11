package com.legacy.legacy_android

import android.media.SoundPool
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.legacy.legacy_android.feature.data.LocationViewModel
import com.legacy.legacy_android.feature.data.user.ACC_TOKEN
import com.legacy.legacy_android.feature.data.user.dataStore
import com.legacy.legacy_android.feature.data.user.getAccToken
import com.legacy.legacy_android.feature.data.user.isTokenValid
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

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
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()
        soundId = soundPool.load(this, R.raw.click, 1)

        val token = getAccToken(this)
        val startDestination = if (isTokenValid(token)) {
            ScreenNavigate.HOME.name
        } else {
            ScreenNavigate.LOGIN.name
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = startDestination) {
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
                    val profileViewModel: ProfileViewModel = hiltViewModel()
                    val locationViewModel: LocationViewModel = hiltViewModel()
                    HomeScreen(
                        modifier = Modifier,
                        viewModel = homeViewModel,
                        navHostController = navController,
                        profileViewModel = profileViewModel,
                        locationViewModel = locationViewModel
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

