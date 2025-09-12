package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun CommentModal(
    viewModel: HomeViewModel = hiltViewModel()
){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Background_Netural, shape = RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "한줄평 남기기",
            style = AppTextStyles.Heading2.bold
        )
        Column {
            Text(text = "#${viewModel.uiState.ruinsDetail?.ruinsId}", style = AppTextStyles.Caption1.Medium, color = Label_Alternative)
            Text(text = "#${viewModel.uiState.ruinsDetail?.ruinsId}", style = AppTextStyles.Headline.bold)
        }
    }
}