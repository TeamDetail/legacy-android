package com.legacy.legacy_android.feature.screen.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.button.LoginButton
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.White

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val context = LocalContext.current
    val activity = context as? android.app.Activity

    BackHandler(enabled = true) {}
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = modifier
                        .width(274.dp)
                        .height(103.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                )
                Text(
                    text = "지역 문화 유산을 쉽게, 레거시",
                    style = AppTextStyles.Headline.medium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Text(
                    text = "소셜 로그인하고 곧바로 뛰어드세요!",
                    style = AppTextStyles.Body2.bold
                )
                LoginButton(
                    onClick = {
                        if (!viewModel.loadingState.value) {
                            viewModel.loginWithKakao(
                                context = context,
                                navHostController = navHostController,
                                onFailure = { error ->
                                    Log.e("LoginScreen", "카카오 로그인 실패", error)
                                }
                            )
                        }
                    },
                    icon = painterResource(R.drawable.kakao),
                    name = "Kakao",
                    bgColor = Color(0xFFFEE500),
                    color = Black,
                    viewModel = viewModel
                )
//                LoginButton(
//                    onClick = {
//                        if (!viewModel.loadingState.value) {
//                            activity?.let {
//                                viewModel.loginWithGoogle(
//                                    activity = it, navHostController = navHostController,
//                                    onFailure = { error ->
//                                        Toast.makeText(
//                                            context,
//                                            "Google 로그인 실패: ${error.message}",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                )
//                            } ?: run {
//                                Toast.makeText(
//                                    context,
//                                    "Activity를 찾을 수 없습니다",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    },
//                    icon = painterResource(R.drawable.google),
//                    name = "Google",
//                    bgColor = White,
//                    color = Black,
//                    viewModel = viewModel
//                )
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = "서비스 약관",
                style = AppTextStyles.Label.Medium.merge(
                    color = Label_Alternative,
                )
            )
            Text(
                text = " · ",
                style = AppTextStyles.Label.Medium.merge(
                    color = Label_Alternative,
                )
            )
            Text(
                text = "개인정보처리방침",
                style = AppTextStyles.Label.Medium.merge(
                    color = Label_Alternative,
                )
            )
        }
    }
}
