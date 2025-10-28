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
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.legacy.legacy_android.feature.data.user.getAccToken
import com.legacy.legacy_android.feature.data.user.getRefToken
import com.legacy.legacy_android.feature.data.user.isTokenValid
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient
import com.legacy.legacy_android.feature.network.fcm.FcmRequest
import com.legacy.legacy_android.feature.network.fcm.FcmService
import com.legacy.legacy_android.feature.screen.LocationViewModel
import com.legacy.legacy_android.feature.screen.achieve.AchieveInfoScreen
import com.legacy.legacy_android.feature.screen.achieve.AchieveScreen
import com.legacy.legacy_android.feature.screen.achieve.AchieveViewModel
import com.legacy.legacy_android.feature.screen.course.CourseCategory
import com.legacy.legacy_android.feature.screen.course.CourseInfo
import com.legacy.legacy_android.feature.screen.course.CourseScreen
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.feature.screen.course.CreateCourse
import com.legacy.legacy_android.feature.screen.friend.FriendScreen
import com.legacy.legacy_android.feature.screen.friend.FriendViewModel
import com.legacy.legacy_android.feature.screen.home.HomeScreen
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.feature.screen.login.LoginScreen
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import com.legacy.legacy_android.feature.screen.market.MarketScreen
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.feature.screen.profile.ProfileEditScreen
import com.legacy.legacy_android.feature.screen.profile.ProfileScreen
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.feature.screen.ranking.RankingScreen
import com.legacy.legacy_android.feature.screen.ranking.RankingViewModel
import com.legacy.legacy_android.feature.screen.setting.SettingScreen
import com.legacy.legacy_android.feature.screen.setting.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ScreenNavigate {
    LOGIN,
    HOME,
    MARKET,
    RANKING,
    ACHIEVE,
    COURSE,
    PROFILE,
    PROFILE_EDIT,
    FRIEND,
    SETTING,
    CREATE_COURSE,
    COURSE_CATEGORY,
    COURSE_INFO,
    ACHIEVE_INFO

}

enum class BgmType(val resourceId: Int, val volume: Float) {
    MAIN(R.raw.mainbgm, 0.6f),
    LOGIN(R.raw.loginbgm, 0.6f),
    MARKET(R.raw.marketbgm, 0.6f)
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fcmService: FcmService
    private val locationViewModel: LocationViewModel by viewModels()

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

    private fun createNotificationChannel(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrofit 초기화
        RetrofitClient.init(this)

        // FCM & 위치, 초기 설정
        refreshAccessTokenIfNeeded()

        enableEdgeToEdge()

        val navigateTo = intent.getStringExtra("navigate_to")
        val startDestination = navigateTo ?: determineStartDestination()

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
                popExitTransition = { ExitTransition.None },
            ) {
                // Course Graph
                navigation(
                    startDestination = ScreenNavigate.COURSE_CATEGORY.name,
                    route = "course_graph"
                ) {
                    composable(ScreenNavigate.COURSE.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("course_graph") }
                        val courseViewModel: CourseViewModel = hiltViewModel(parentEntry)
                        CourseScreen(Modifier, courseViewModel, navController)
                    }
                    composable(ScreenNavigate.COURSE_CATEGORY.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("course_graph") }
                        val courseViewModel: CourseViewModel = hiltViewModel(parentEntry)
                        CourseCategory(Modifier, courseViewModel, navController)
                    }
                    composable(ScreenNavigate.CREATE_COURSE.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("course_graph") }
                        val courseViewModel: CourseViewModel = hiltViewModel(parentEntry)
                        CreateCourse(Modifier, courseViewModel, navController)
                    }
                    composable(ScreenNavigate.COURSE_INFO.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("course_graph") }
                        val courseViewModel: CourseViewModel = hiltViewModel(parentEntry)
                        CourseInfo(Modifier, courseViewModel, navController)
                    }
                }

                // Profile Graph
                navigation(
                    startDestination = ScreenNavigate.PROFILE.name,
                    route = "profile_graph"
                ) {
                    composable(ScreenNavigate.PROFILE.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("profile_graph") }
                        val profileViewModel: ProfileViewModel = hiltViewModel(parentEntry)
                        ProfileScreen(Modifier, profileViewModel, navController)
                    }
                    composable(ScreenNavigate.PROFILE_EDIT.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("profile_graph") }
                        val profileViewModel: ProfileViewModel = hiltViewModel(parentEntry)
                        ProfileEditScreen(Modifier, profileViewModel, navController)
                    }
                }

                // Achieve Graph
                navigation(
                    startDestination = ScreenNavigate.ACHIEVE.name,
                    route = "achieve_graph"
                ){
                    composable(ScreenNavigate.ACHIEVE.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("achieve_graph") }
                        val achieveViewModel: AchieveViewModel = hiltViewModel(parentEntry)
                        AchieveScreen(Modifier, achieveViewModel, navController)
                    }
                    composable(ScreenNavigate.ACHIEVE_INFO.name) { backStackEntry ->
                        val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("achieve_graph") }
                        val achieveViewModel: AchieveViewModel = hiltViewModel(parentEntry)
                        AchieveInfoScreen(Modifier, achieveViewModel, navController)
                    }
                }

                // Other Screens
                composable(ScreenNavigate.LOGIN.name) {
                    val loginViewModel: LoginViewModel = hiltViewModel()
                    LoginScreen(Modifier, loginViewModel, navController)
                }
                composable(ScreenNavigate.HOME.name) { backStackEntry ->
                    val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
                    val profileViewModel: ProfileViewModel = hiltViewModel(backStackEntry)
                    val locationViewModel: LocationViewModel = hiltViewModel(backStackEntry)
                    HomeScreen(Modifier, homeViewModel, profileViewModel, locationViewModel, navController)
                }
                composable(ScreenNavigate.MARKET.name) {
                    val marketViewModel: MarketViewModel = hiltViewModel()
                    MarketScreen(Modifier, marketViewModel, navController)
                }
                composable(ScreenNavigate.RANKING.name) {
                    val rankingViewModel: RankingViewModel = hiltViewModel()
                    RankingScreen(Modifier, rankingViewModel, navController)
                }
                composable(ScreenNavigate.FRIEND.name) {
                    val friendViewModel: FriendViewModel = hiltViewModel()
                    FriendScreen(Modifier, friendViewModel, navController)
                }
                composable(ScreenNavigate.SETTING.name) {
                    val settingViewModel: SettingViewModel = hiltViewModel()
                    SettingScreen(Modifier, settingViewModel, navController)
                }
            }
        }
    }

    private fun refreshAccessTokenIfNeeded() {
        createNotificationChannel("channel_id", "Default Channel")
        setupFullScreenMode()
        initializeSoundPool()
        registerLifecycleObserver()

        locationViewModel.getCurrentLocation { location ->
            if (location == null) {
                Log.e("FCM", "위치를 가져오지 못했습니다.")
                return@getCurrentLocation
            }

            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) return@addOnCompleteListener

                val token = task.result
                Log.d("FCM", "토큰: $token")
                lifecycleScope.launch {
                    try {
                        fcmService.location(
                            FcmRequest(
                                lat = location.latitude,
                                lng = location.longitude,
                                title = "앱 시작 시 위치 등록",
                                targetToken = token
                            )
                        )
                    } catch (e: Exception) {
                        Log.e("FCM", "FCM 위치 등록 실패: ${e.message}")
                    }
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
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    private fun initializeSoundPool() {
        soundPool = SoundPool.Builder().setMaxStreams(5).build()
        soundId = soundPool.load(this, R.raw.click, 1)
    }

    private fun registerLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    private fun determineStartDestination(): String {
        val accessToken = getAccToken(this)
        val refreshToken = getRefToken(this)

        return when {
            isTokenValid(accessToken) -> {
                Log.d("MainActivity", "유효한 accessToken 존재")
                ScreenNavigate.HOME.name
            }

            !refreshToken.isNullOrEmpty() -> {
                Log.d("MainActivity", "refreshToken 존재하지만 accessToken 없음 -> 로그인 필요")
                ScreenNavigate.LOGIN.name
            }

            else -> {
                Log.d("MainActivity", "유효한 토큰 없음 - 로그인 필요")
                ScreenNavigate.LOGIN.name
            }
        }
    }

    private fun handleDestinationChange(route: String?) {
        val targetBgm = when (route) {
            ScreenNavigate.LOGIN.name -> BgmType.LOGIN
            ScreenNavigate.MARKET.name -> BgmType.MARKET
            else -> BgmType.MAIN
        }
        if (currentBgm != targetBgm) switchBgm(targetBgm)
    }

    private fun switchBgm(bgmType: BgmType) {
        try {
            stopAndReleaseMediaPlayer()
            mediaPlayer = MediaPlayer.create(this, bgmType.resourceId)?.apply {
                isLooping = true
                setVolume(bgmType.volume, bgmType.volume)
                start()
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
            if (isPlaying) stop()
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
