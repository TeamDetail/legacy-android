package com.legacy.legacy_android.feature.screen.achieve

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.achieve.AchieveBox
import com.legacy.legacy_android.res.component.achieve.AchieveBoxSkeleton
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.modal.ClaimModal
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Yellow_Netural

@Composable
fun AchieveScreen(
    modifier: Modifier = Modifier,
    viewModel: AchieveViewModel = hiltViewModel(),
    navHostController: NavHostController){

    val statusList = listOf("전체", "탐험", "숙련", "히든")
    LaunchedEffect(Unit) {
        viewModel.fetchAllAchieveList()
        viewModel.updateCurrentAchieve(null)
    }

    if (viewModel.uiState.isClaimModalOpen){
        ClaimModal(viewModel)
    }
    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController,
    ) {
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
                            onClick = {
                                viewModel.changeAchieveStatus(index)
                                if (index == 0){
                                    viewModel.fetchAllAchieveList()
                                }else if (index == 1){
                                    viewModel.fetchAchieveListByType("EXPLORE")
                                }else if (index == 2){
                                viewModel.fetchAchieveListByType("LEVEL")
                                }else{
                                    viewModel.fetchAchieveListByType("HIDDEN")
                                }
                            },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Netural
                        )
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Yellow_Netural, shape = RoundedCornerShape(8.dp))
                    .clickable{viewModel.claimAward() }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = "보상 일괄 수령",
                    color = Yellow_Netural,
                    style = AppTextStyles.Caption1.Bold
                )
            }
            Column {
                if (viewModel.uiState.isLoading) {
                    repeat(3) {
                        AchieveBoxSkeleton(modifier)
                    }
                } else if (viewModel.uiState.achieveList?.isEmpty() == true){
                    Text("도전과제가 없습니다.", style = AppTextStyles.Caption1.Bold)
                } else {
                    viewModel.uiState.achieveList?.forEach { it ->
                        AchieveBox(modifier, it, viewModel, navHostController)
                    }
                }
            }
        }
    }
}
