package com.legacy.legacy_android.feature.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.ScreenNavigate
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Netural80
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
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
                    modifier = Modifier
                        .width(274.dp)
                        .height(103.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                )
                Text(
                    text = "지역 문화 유산을 쉽게, 레거시",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = White,
                        textAlign = TextAlign.Center,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Text(
                    text = "소셜 로그인하고 곧바로 뛰어드세요!",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = White,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )
                )

                Box(
                    modifier = Modifier
                        .width(321.dp)
                        .height(54.dp)
                        .background(Black, shape = RoundedCornerShape(16.dp))
                        .clickable{navHostController.navigate(ScreenNavigate.HOME.name)},
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(31.dp),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = null,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )

                        Text(
                            text = "카카오톡 로그인",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 24.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                                color = Label,
                                textAlign = TextAlign.Center,
                            ),
                        )
                    }
                }

            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 800.dp)
        ) {
            Text(
                text = "서비스 약관",
                style = TextStyle(
                    color = Netural80,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                )
            )
            Text(
                text = " · ",
                style = TextStyle(
                    color = Netural80,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                )
            )
            Text(
                text = "개인정보처리방침",
                style = TextStyle(
                    color = Netural80,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                )
            )
        }
    }
}
