package com.legacy.legacy_android.feature.screen.achieve

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.ComingSoon
import com.legacy.legacy_android.res.component.achieve.AchieveBox
import com.legacy.legacy_android.res.component.bars.infobar.InfoBarViewModel
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.profile.Statbar
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Yellow_Netural

@Composable
fun AchieveScreen(
    modifier: Modifier = Modifier,
    viewModel: AchieveViewModel = hiltViewModel(),
    navHostController: NavHostController){
    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController,
    ) {
        val statusList = listOf("전체", "일일", "탐험", "숙련", "시련", "히든")
        TitleBox(title = "도전과제", image = R.drawable.medal)
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statusList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.uiState.achieveStatus,
                            onClick = { viewModel.changeAchieveStatus(index) },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Netural
                        )
                    }
                }
            }
            Statbar(modifier, 15/500f, "", "15 / 500", "", Primary)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Yellow_Netural, shape = RoundedCornerShape(8.dp))
                    .clickable{}
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = "보상 일괄 수령",
                    color = Yellow_Netural,
                    style = AppTextStyles.Caption1.Bold
                )
            }
            // 여기에 도전과제 박스 넣기
            Column {
                AchieveBox(modifier)
                AchieveBox(modifier)
                AchieveBox(modifier)
            }
        }
    }
}
