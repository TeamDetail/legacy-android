package com.legacy.legacy_android.feature.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.button.BackButton
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.modal.ItemModal
import com.legacy.legacy_android.res.component.modal.OpenCardModal
import com.legacy.legacy_android.res.component.profile.DictionaryInfo
import com.legacy.legacy_android.res.component.profile.InventoryInfo
import com.legacy.legacy_android.res.component.profile.Scorebar
import com.legacy.legacy_android.res.component.profile.StatTable
import com.legacy.legacy_android.res.component.profile.StatTableResponse
import com.legacy.legacy_android.res.component.profile.Statbar
import com.legacy.legacy_android.res.component.profile.TitleSelector
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.res.component.title.TitleBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Normal

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
){
    val statusList = listOf("기록", "칭호", "도감", "인벤토리")
    val profile by viewModel.profileFlow.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.clearProfile()
        viewModel.fetchProfile()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
            .padding(vertical = 40.dp, horizontal = 20.dp)
    ) {
        if (viewModel.uiState.openCardResponse != null){
            OpenCardModal(viewModel)
        }
        if (viewModel.uiState.cardPackOpen){
            ItemModal(viewModel)
        }
        Box(
            modifier = modifier.align(Alignment.BottomCenter).zIndex(999f)
        ) {
            if (viewModel.uiState.selectedItem != null) {
                InventoryInfo(viewModel)
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .align(Alignment.TopStart)
                .verticalScroll(rememberScrollState())
        ) {
            BackButton(selectedId = 2, title = "프로필", navHostController = navHostController)
            // 여기서 프로필 윗부분
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier =
                    modifier.padding(top = 24.dp)
            ) {
                if (profile == null) {
                    SkeletonBox(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SkeletonBox(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(0.4f)
                                .clip(CircleShape),
                        )
                        SkeletonBox(
                            modifier = Modifier
                                .height(16.dp)
                                .fillMaxWidth(0.2f)
                                .clip(CircleShape),
                        )
                        SkeletonBox(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth(0.3f)
                                .clip(RoundedCornerShape(4.dp)),
                        )
                    }
                } else {
                    AsyncImage(
                        model = profile?.imageUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.temp_profile),
                        error = painterResource(R.drawable.temp_profile)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = profile?.nickname ?: "없음",
                                style = AppTextStyles.Title3.bold
                            )
                            Image(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp).clickable {
                                    viewModel.setSelectedItem(null)
                                    navHostController.navigate("profile_edit") }
                            )
                        }
                        Text(
                            text = "LV. ${profile?.level}",
                            color = Label_Alternative,
                            style = AppTextStyles.Body1.bold
                        )
                        profile?.title?.name?.takeIf { it.isNotBlank() }?.let {
                            TitleBar(title = it)
                        }
                    }
                }

            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statusList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.uiState.profileStatus,
                            onClick = { viewModel.changeProfileStatus(index)
                                viewModel.setSelectedItem(null)
                                if(item == "도감"){viewModel.fetchMyCollection("경기")} },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Netural
                        )
                    }
                }
            }

            when (viewModel.uiState.profileStatus) {
                0 -> RecordScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                )
                1 -> TitleScreen(
                    modifier = modifier,
                    viewModel = viewModel
                )
                2 -> { DictionaryScreen(
                    modifier = modifier,
                    viewModel = viewModel
                )
                }
                3 -> InventoryScreen(
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
    val expStat = listOf(
        StatTableResponse("완수한 총 도전과제", profile?.record?.experience?.adventureAchieve.toString()),
        StatTableResponse("완수한 히든 도전과제", profile?.record?.experience?.hiddenAchieve.toString()),
        StatTableResponse("완수한 탐험 도전과제", profile?.record?.experience?.experienceAchieve.toString()),
        StatTableResponse("완수한 숙련 도전과제", profile?.record?.experience?.exp.toString()),
        StatTableResponse("수집한 카드", profile?.record?.experience?.cardCount.toString()),
        StatTableResponse("수집한 찬란한 카드", profile?.record?.experience?.shiningCardCount.toString()),
        StatTableResponse("소지한 칭호", profile?.record?.experience?.titleCount.toString()),
        StatTableResponse("가입일자", profile?.record?.experience?.createdAt?.substring(0, 10))
    )
    val advStat = listOf(
        StatTableResponse("탐험 완료한 유적지", profile?.record?.adventure?.allBlocks?.toString()),
        StatTableResponse("맞춘 퀴즈", profile?.record?.adventure?.solvedQuizzes?.toString()),
        StatTableResponse("남긴 한줄평", profile?.record?.adventure?.commentCount?.toString()),
        StatTableResponse("완료한 코스", profile?.record?.adventure?.clearCourse?.toString()),
        StatTableResponse("제작한 코스", profile?.record?.adventure?.makeCourse?.toString()),
        StatTableResponse("틀린 퀴즈", profile?.record?.adventure?.wrongQuizzes?.toString())
    )
    // 스탯 바
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Scorebar(
            title = "자기소개",
            text = if (profile?.description.isNullOrBlank()) "자기소개가 없습니다." else profile!!.description,
            modifier = modifier
        )
        // 숙련
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "숙련", style = AppTextStyles.Title3.bold)
            Text(
                text = buildAnnotatedString {
                    append(
                        text = "#${profile?.record?.experience?.rank.toString()}"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Label,
                        )
                    ) {
                        append("위")
                    }
                },
                style = AppTextStyles.Title3.bold,
                color = Red_Normal
            )
        }
        Statbar(
            modifier = modifier,
            percent = profile?.record?.experience?.exp?.toFloat()?.div(13000) ?: 0f,
            text = "Lv. ${profile?.level}  ",
            subtext = "${profile?.record?.experience?.exp} / 13000",
            barColor = Red_Normal
        )
        StatTable(data = expStat)
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "탐험", style = AppTextStyles.Title3.bold)
            Text(
                text = buildAnnotatedString {
                    append(
                        text = "#${profile?.record?.adventure?.rank.toString()}"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Label,
                        )
                    ) {
                        append("위")
                    }
                },
                style = AppTextStyles.Title3.bold,
                color = Blue_Netural
            )
        }
        Statbar(
            modifier = modifier,
            percent = profile?.record?.adventure?.allBlocks?.toFloat()?.div(13000) ?: 0f,
            text = "총 ${profile?.record?.adventure?.allBlocks}블록 탐험",
            barColor = Blue_Netural
        )
        StatTable(data = advStat)
    }
    Spacer(modifier = modifier.height(8.dp))
}

@Composable
fun TitleScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
){

}

@Composable
fun DictionaryScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val statusList = listOf("경기", "강원", "경북", "경남", "전북", "전남", "충북", "충남", "제주")
    Row (
        modifier = modifier.fillMaxSize()
    ){
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier.fillMaxWidth(0.3f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                statusList.forEachIndexed { index, item ->
                    TitleSelector(
                        viewModel.uiState.myCards?.maxCount ?: 0, 42,
                        onClick = {
                            viewModel.changeTitleStatus(index)
                            viewModel.fetchMyCollection(item)
                        },
                        id = index,
                        selectedValue = viewModel.uiState.titleStatus,
                        text = item,
                        modifier
                    )
                }
            }
            LazyColumn (
                modifier = modifier.fillMaxWidth(0.95f).height(1000.dp)
            ) {
                items(viewModel.uiState.myCards?.cards?.chunked(2) ?: emptyList()) { cardPair ->
                    Row {
                        cardPair.forEach { card ->
                            DictionaryInfo(
                                item = card
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InventoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val inventory = viewModel.uiState.myInventory ?: emptyList()

    LaunchedEffect(Unit) {
        viewModel.setSelectedItem(null)
        viewModel.fetchMyInventory()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = modifier.fillMaxSize().height(1000.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(105) { index ->
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .background(Fill_Normal, RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = Line_Alternative
                        ).clickable {
                            if (index < inventory.size) {
                                viewModel.setSelectedItem(viewModel.uiState.myInventory!![index])
                                println(viewModel.uiState.myInventory)
                            } else {
                                viewModel.setSelectedItem(null)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (index < inventory.size) {
                        Text(text = inventory[index].itemName)
                    }
                }
            }
        }
    }
}
