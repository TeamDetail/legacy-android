package com.legacy.legacy_android.feature.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.res.component.bars.infobar.InfoBarViewModel
import com.legacy.legacy_android.res.component.button.BackArrow
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.profile.Scorebar
import com.legacy.legacy_android.res.component.profile.Statbar
import com.legacy.legacy_android.res.component.title.TitleBar
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Blue_Natural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Natural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Normal
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
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

            // 여기서부터 작성
                // 여기서 프로필 윗부분
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier =
                        modifier.padding(top = 24.dp)
                ) {
                    // 프로필 이미지
                    AsyncImage(
                        model = profile?.imageUrl,
                        contentDescription = "프로필 이미지",
                        modifier = modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        error = painterResource(R.drawable.temp_profile)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // 이름
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = profile?.nickname ?: "null",
                                color = Label,
                                fontSize = 28.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                            )
                            Image(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = null,
                                modifier = modifier.size(32.dp)
                            )
                        }
                        // level
                        Text(
                            text = "LV. ${viewModel.profile?.level}",
                            color = Label_Alternative,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                        )
                        // 칭호
                        TitleBar(
                            title = profile?.title?.name ?: "칭호가 없습니다"
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            viewModel.profileMode.forEachIndexed { index, item ->
                                StatusButton(
                                    selectedValue = viewModel.profileStatus,
                                    onClick = { viewModel.profileStatus = index },
                                    text = item,
                                    id = index,
                                    selectedColor = Primary,
                                    nonSelectedColor = Line_Natural
                                )
                            }
                        }
                    }
                }
            when (viewModel.profileStatus) {
                0 -> RecordScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                )
                1 ->TitleScreen(
                    modifier = modifier,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile = viewModel.profile
    // 스탯 바
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // 숙련
        Statbar(
            modifier = modifier,
            name = "숙련",
            text = "Lv. ${profile?.level}",
            percent = 0.6f,
            subtext = "(7000/13000)",
            barColor = Red_Normal
        )
        // 시련
        Statbar(
            modifier = modifier,
            name = "시련",
            text = "최고 160층",
            barColor = Primary,
            percent = 0.6f,
            subtext = ""
        )
        // 탐험
        Statbar(
            modifier = modifier,
            name = "탐험",
            text = "카드 300개 수집",
            percent = 0.6f,
            subtext = "",
            barColor = Blue_Natural
        )
    }
    Spacer(modifier = modifier.height(16.dp))
    // 스코어
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),){
        Scorebar(
            modifier =  modifier,
            title = "시련 최고 점수",
            text = "${profile?.maxScore}"
        )
        Scorebar(
            modifier = modifier,
            title = "탐험한 일반 블록 수",
            text = "${profile?.allBlocks}"
        )
        Scorebar(
            modifier = modifier,
            title = "탐험한 유적지 블록 수",
            text = "${profile?.ruinsBlocks}"
        )
        Scorebar(
            modifier = modifier,
            title = "사용한 크레딧",
            text = "${profile?.stats?.creditCollect}"
        )
    }
}

@Composable
fun TitleScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
){

}
