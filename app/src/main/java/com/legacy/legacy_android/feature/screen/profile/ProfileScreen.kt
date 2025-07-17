package com.legacy.legacy_android.feature.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.res.component.button.BackButton
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.profile.Scorebar
import com.legacy.legacy_android.res.component.profile.Statbar
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.res.component.title.TitleBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Normal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
){
    val profile by viewModel.profileFlow.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }
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
            BackButton(selectedId = selectedId, title = "프로필", navHostController = navHostController)
                // 여기서 프로필 윗부분
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier =
                        modifier.padding(top = 24.dp)
                ) {
                    // 프로필 이미지
                    ProfileImageWithSkeleton(
                        imageUrl = profile?.imageUrl,
                        modifier = modifier
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
                                style = AppTextStyles.Title3.bold
                            )
                            Image(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = null,
                                modifier = modifier.size(32.dp)
                            )
                        }
                        // level
                        Text(
                            text = "LV. ${profile?.level}",
                            color = Label_Alternative,
                            style = AppTextStyles.Body1.bold
                        )
                        // 칭호
                        profile?.title?.name?.takeIf { !it.isNullOrBlank() }?.let { titleName ->
                            TitleBar(title = titleName)
                        }
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
                                    nonSelectedColor = Line_Netural
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
    val profile by viewModel.profileFlow.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }
    // 스탯 바
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // 숙련
        Statbar(
            modifier = modifier,
            title = "숙련",
            text = "Lv. ${profile?.level}",
            percent = 0.6f,
            subtext = "(7000/13000)",
            barColor = Red_Normal
        )
        // 시련
        Statbar(
            modifier = modifier,
            title = "시련",
            text = "최고 160층",
            barColor = Primary,
            percent = 0.6f,
            subtext = ""
        )
        // 탐험
        Statbar(
            modifier = modifier,
            title = "탐험",
            text = "카드 300개 수집",
            percent = 0.6f,
            subtext = "",
            barColor = Blue_Netural
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
            text = "${NumberFormat.getNumberInstance(Locale.US).format(profile?.maxScore)} 문명 점수"
        )
        Scorebar(
            modifier = modifier,
            title = "탐험한 일반 블록 수",
            text = "${NumberFormat.getNumberInstance(Locale.US).format(profile?.allBlocks)} 블록"
        )
        Scorebar(
            modifier = modifier,
            title = "탐험한 유적지 블록 수",
            text = "${NumberFormat.getNumberInstance(Locale.US).format(profile?.ruinsBlocks)} 블록"
        )
        Scorebar(
            modifier = modifier,
            title = "사용한 크레딧",
            text = NumberFormat.getNumberInstance(Locale.US).format(profile?.stats?.creditCollect)
        )
    }
}

@Composable
fun TitleScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
){

}


@Composable
fun ProfileImageWithSkeleton(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    Box(modifier = modifier.size(100.dp)) {
        if (isLoading && !isError) {
            SkeletonBox(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            onLoading = { isLoading = true },
            onSuccess = {
                isLoading = false
                isError = false
            },
            onError = {
                isLoading = false
                isError = true
            },
            error = painterResource(R.drawable.temp_profile)
        )
    }
}