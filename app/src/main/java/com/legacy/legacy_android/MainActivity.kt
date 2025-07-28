package com.legacy.legacy_android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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
import com.legacy.legacy_android.feature.screen.course.CourseScreen
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import dagger.hilt.android.AndroidEntryPoint

enum class ScreenNavigate {
    LOGIN,
    HOME,
    MARKET,
    RANKING,
    ACHIEVE,
    COURSE,
    PROFILE,
    FRIEND,
    SETTING
}

enum class BgmType(val resourceId: Int, val volume: Float) {
    MAIN(R.raw.mainbgm, 0.6f),
    MARKET(R.raw.loginbgm, 0.4f),
    LOGIN(R.raw.loginbgm, 0.6f)
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    // Notification 수신을 위한 체널 추가
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0

    private var mediaPlayer: MediaPlayer? = null
    private var currentBgm: BgmType? = null

    private val lifecycleObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> resumeMusic()
            Lifecycle.Event.ON_STOP -> pauseMusic()
            else -> Unit
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val configuration = Configuration(newBase.resources.configuration)
        configuration.fontScale = 1.0f
        val context = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFullScreenMode()
        initializeSoundPool()
        registerLifecycleObserver()
        val startDestination = determineStartDestination()


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token= task.result

            val msg = getString(R.string.msg_token_token_fmt, token)
            Log.d("MainActivity", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            navController.addOnDestinationChangedListener { _, destination, _ ->
                handleDestinationChange(destination.route)
            }

            NavHost(
                navController = navController,
                startDestination = startDestination,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
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
                composable(route = ScreenNavigate.COURSE.name) {
                    val courseViewModel: CourseViewModel = hiltViewModel()
                    CourseScreen(Modifier, courseViewModel, navController)
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

    private fun setupFullScreenMode() {
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
    }

    private fun initializeSoundPool() {
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()
        soundId = soundPool.load(this, R.raw.click, 1)
    }

    private fun registerLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    private fun determineStartDestination(): String {
        val refreshToken = getRefToken(this)
        val accessToken = getAccToken(this)

        return when {
            isTokenValid(accessToken) -> ScreenNavigate.HOME.name
            isTokenValid(refreshToken) -> ScreenNavigate.HOME.name
            else -> ScreenNavigate.LOGIN.name
        }
    }

    private fun handleDestinationChange(route: String?) {
        val targetBgm = when (route) {
            ScreenNavigate.MARKET.name -> BgmType.MARKET
            ScreenNavigate.LOGIN.name -> BgmType.LOGIN
            else -> BgmType.MAIN
        }

        if (currentBgm != targetBgm) {
            switchBgm(targetBgm)
        }
    }

    private fun switchBgm(bgmType: BgmType) {
        try {
            stopAndReleaseMediaPlayer()

            mediaPlayer = MediaPlayer.create(this, bgmType.resourceId)?.apply {
                isLooping = true
                setVolume(bgmType.volume, bgmType.volume)
//                start()
            }
            currentBgm = bgmType
        } catch (e: Exception) {
                Log.e("MainActivity", "BGM 전환 중 오류 발생: ${e.message}")
        }
    }

    private fun resumeMusic() {
        mediaPlayer?.start()
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
    }

    private fun stopAndReleaseMediaPlayer() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
            stopAndReleaseMediaPlayer()
            soundPool.release()
        } catch (e: Exception) {
            Log.e("MainActivity", "리소스 해제 중 오류 발생: ${e.message}")
        }
    }
}