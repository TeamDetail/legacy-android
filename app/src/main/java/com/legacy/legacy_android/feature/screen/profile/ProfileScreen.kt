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
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.res.component.button.BackButton
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.profile.DictionaryInfo
import com.legacy.legacy_android.res.component.profile.InventoryInfo
import com.legacy.legacy_android.res.component.profile.Scorebar
import com.legacy.legacy_android.res.component.profile.Statbar
import com.legacy.legacy_android.res.component.profile.TitleSelector
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.res.component.title.TitleBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Normal
import com.legacy.legacy_android.ui.theme.White

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
    val selectedId = Nav.getNavStatus()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
            .padding(vertical = 40.dp, horizontal = 20.dp)
    ) {
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
            BackButton(selectedId = selectedId, title = "프로필", navHostController = navHostController)
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
                                modifier = Modifier.size(32.dp).clickable { navHostController.navigate("profile_edit") }
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
    // 스탯 바
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Scorebar(title = "자기소개", text = "자기소개", modifier = modifier)
        // 숙련
        Spacer(modifier = modifier.height(8.dp))
        Statbar(
            modifier = modifier,
            title = "숙련",
            text = "Lv. ${profile?.level ?: "-"}",
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
    Spacer(modifier = modifier.height(8.dp))
    // 스코어
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),){
        Scorebar(
            modifier =  modifier,
            title = "시련 최고 점수",
            text = "${profile?.maxScore ?: "-"} 문명 점수"
        )
        Scorebar(
            modifier = modifier,
            title = "탐험한 일반 블록 수",
            text = "${profile?.allBlocks ?: "-"} 블록"
        )
        Scorebar(
            modifier = modifier,
            title = "탐험한 유적지 블록 수",
            text = "${profile?.ruinsBlocks ?: "-"} 블록"
        )
        Scorebar(
            modifier = modifier,
            title = "사용한 크레딧",
            text = "${profile?.stats?.creditCollect ?: "-"} 크레딧"
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
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.fillMaxWidth(0.95f)
            ) {
                items(viewModel.uiState.myCards?.cards?.chunked(2) ?: emptyList()) { cardPair ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
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
