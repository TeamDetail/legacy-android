package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Line_Alternative

@Composable
fun InventoryInfo(
    viewModel: ProfileViewModel = hiltViewModel()) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Background_Netural, shape = RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.cardpack),
                contentDescription = null,
                modifier = Modifier.size(120.dp).clip(RoundedCornerShape(8.dp))
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                ){
                    Text(
                        text = viewModel.uiState.selectedItem?.itemName ?: "이름 없음",
                        style = AppTextStyles.Heading1.bold
                    )
                    Text(
                        text = "${viewModel.uiState.selectedItem?.itemCount}개 보유",
                        style = AppTextStyles.Caption1.Bold,
                        color = Label_Alternative
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    text = viewModel.uiState.selectedItem?.itemDescription ?: "설명 없음",
                    style = AppTextStyles.Body2.medium,
                    color = Label_Netural
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                .border(1.dp, color = Line_Alternative, shape = RoundedCornerShape(12.dp))
                .clickable{viewModel.updateCardPackOpen(true)}
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "사용하기",
                color = Label_Netural,
                style = AppTextStyles.Body1.bold
            )
        }
    }
}