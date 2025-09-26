package com.legacy.legacy_android.feature.screen.login

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Netural80

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    BackHandler (enabled = true) {}
    val context = LocalContext.current
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
                verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Text(
                    text = "소셜 로그인하고 곧바로 뛰어드세요!",
                    style = AppTextStyles.Body2.bold
                )
                Box(
                    modifier = modifier
                        .width(321.dp)
                        .height(54.dp)
                        .background(Color(0xFFFEE500), shape = RoundedCornerShape(16.dp))
                        .clickable (
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ){
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
                    contentAlignment = Alignment.Center
                ) {
                    if (viewModel.loadingState.value != true) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.kakao),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "카카오 로그인",
                                style = AppTextStyles.Body2.medium.merge(
                                    TextStyle(
                                        color = Color(0xFF181600),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box(modifier = Modifier.width(20.dp))
                        }
                    }else{
                        Text("로그인 중입니다...",
                            style = AppTextStyles.Body2.medium.merge(
                                TextStyle(
                                    color = Color(0xFF181600),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .align(Alignment.Center)
                .padding(top = 800.dp)
        ) {
            Text(
                text = "서비스 약관",
                style = AppTextStyles.Label.Medium.merge(
                    color = Netural80,
                )
            )
            Text(
                text = " · ",
                style = AppTextStyles.Label.Medium.merge(
                    color = Netural80,
                )
            )
            Text(
                text = "개인정보처리방침",
                style = AppTextStyles.Label.Medium.merge(
                    color = Netural80,
                )
            )
        }
    }
}
