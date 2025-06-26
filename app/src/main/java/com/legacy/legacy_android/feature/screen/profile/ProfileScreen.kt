package com.legacy.legacy_android.feature.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.res.component.button.BackArrow
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Yellow
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }
    val profile = viewModel.profile
    val selectedId = Nav.getNavStatus()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
             BackArrow(navHostController = navHostController, selectedId = selectedId)
                Text(
                    text = "프로필",
                    color = Label,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                )
            }
            // 여기서 프로필 윗부분
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier =
                    Modifier.padding(top = 24.dp)
            ) {
                AsyncImage(
                    model = profile?.imageUrl,
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.temp_profile)
                )
                Column(
                    modifier = Modifier
                        .width(120.dp)
                ){
                    Text(
                        text = profile?.nickname ?: "사용자",
                        color = Label,
                        fontSize = 28.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "LV. ${viewModel.profile?.level}",
                        color = Label,
                        fontSize = 16.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                    )
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = Yellow, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = viewModel.profile?.title.toString(),
                            color = Yellow,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .padding(vertical = 2.dp, horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}