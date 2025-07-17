package com.legacy.legacy_android

import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.legacy.legacy_android.feature.data.LocationViewModel
import com.legacy.legacy_android.feature.data.user.getAccToken
import com.legacy.legacy_android.feature.data.user.getRefToken
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
import com.legacy.legacy_android.feature.screen.setting.SettingScreen
import com.legacy.legacy_android.feature.screen.setting.SettingViewModel
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
    FRIEND,
    SETTING
}

enum class BgmType {
    MAIN, MARKET, LOGIN
}


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0

    private var mediaPlayer: MediaPlayer? = null
    private var currentBgm: BgmType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)

            window.decorView.post {
                window.insetsController?.let { controller ->
                    controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()
        soundId = soundPool.load(this, R.raw.click, 1)
        val refreshToken = getRefToken(this)
        val accessToken = getAccToken(this)

        val startDestination = when {
            isTokenValid(accessToken) -> {
                ScreenNavigate.HOME.name
            }
            isTokenValid(refreshToken) -> {
                ScreenNavigate.HOME.name
            }
            else -> {
                ScreenNavigate.LOGIN.name
            }
        }


        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mediaPlayer?.start()
                Lifecycle.Event.ON_STOP -> mediaPlayer?.pause()
                else -> Unit
            }
        })
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            navController.addOnDestinationChangedListener { _: NavController, destination, _ ->
                when (destination.route) {
                    ScreenNavigate.MARKET.name -> {
                        if (currentBgm != BgmType.MARKET) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = MediaPlayer.create(this, R.raw.marketbgm).apply {
                                isLooping = true
                                setVolume(0.4f, 0.4f)
                                start()
                            }
                            currentBgm = BgmType.MARKET
                        }
                    }

                    ScreenNavigate.LOGIN.name -> {
                        if (currentBgm != BgmType.LOGIN) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = MediaPlayer.create(this, R.raw.loginbgm).apply {
                                isLooping = true
                                setVolume(0.6f, 0.6f)
                                start()
                            }
                            currentBgm = BgmType.LOGIN
                        }
                    }

                    else -> {
                        if (currentBgm != BgmType.MAIN) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = MediaPlayer.create(this, R.raw.mainbgm).apply {
                                isLooping = true
                                setVolume(0.6f, 0.6f)
                                start()
                            }
                            currentBgm = BgmType.MAIN
                        }
                    }
                }
            }


            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = ScreenNavigate.LOGIN.name) {
                    val loginViewModel: LoginViewModel = hiltViewModel()
                    LoginScreen(Modifier, loginViewModel, navController)
                }
                composable(route = ScreenNavigate.HOME.name) {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    val profileViewModel: ProfileViewModel = hiltViewModel()
                    val locationViewModel: LocationViewModel = hiltViewModel()
                    HomeScreen(Modifier, homeViewModel, profileViewModel, locationViewModel, navController)
                }
                composable(route = ScreenNavigate.MARKET.name) {
                    val marketViewModel: MarketViewModel = hiltViewModel()
                    MarketScreen(Modifier, marketViewModel, navController)
                }
                composable(route = ScreenNavigate.ACHIEVE.name) {
                    val achieveViewModel: AchieveViewModel = hiltViewModel()
                    AchieveScreen(Modifier, achieveViewModel, navController)
                }
                composable(route = ScreenNavigate.TRIAL.name) {
                    val trialViewModel: TrialViewModel = hiltViewModel()
                    TrialScreen(Modifier, trialViewModel, navController)
                }
                composable(route = ScreenNavigate.RANKING.name) {
                    val rankingViewModel: RankingViewModel = hiltViewModel()
                    RankingScreen(Modifier, rankingViewModel, navController)
                }
                composable(route = ScreenNavigate.PROFILE.name) {
                    val profileViewModel: ProfileViewModel = hiltViewModel()
                    ProfileScreen(Modifier, profileViewModel, navController)
                }
                composable(route = ScreenNavigate.FRIEND.name) {
                    val friendViewModel: FriendViewModel = hiltViewModel()
                    FriendScreen(Modifier, friendViewModel, navController)
                }
                composable(route = ScreenNavigate.SETTING.name) {
                    val settingViewModel: SettingViewModel = hiltViewModel()
                    SettingScreen(Modifier, settingViewModel, navController)
                }
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}
