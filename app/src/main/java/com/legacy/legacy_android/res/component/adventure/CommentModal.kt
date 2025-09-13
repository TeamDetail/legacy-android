package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White

@Composable
fun CommentModal(
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.updateCommentRate(0)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Background_Netural, shape = RoundedCornerShape(20.dp))
            .padding(12.dp)
            .imePadding()
            .clickable{
                viewModel.fetchRuinsDetail(viewModel.uiState.ruinsDetail!!.ruinsId)
                viewModel.updateIsCommenting(true)}
    ) {
        Text(
            text = "한줄평 남기기",
            style = AppTextStyles.Heading2.bold,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "#${viewModel.uiState.ruinsDetail?.ruinsId}",
                style = AppTextStyles.Caption1.Medium,
                color = Label_Alternative
            )
            Text(
                text = "${viewModel.uiState.ruinsDetail?.name}",
                style = AppTextStyles.Headline.bold
            )
        }
        Row(modifier = Modifier.fillMaxWidth().clickable{viewModel.updateCommentModal(true)
                                                        println(viewModel.uiState.isCommentModalOpen)},) {
            for (i in 1..10) {
                if (i % 2 != 0) {
                    Image(
                        painter = painterResource(R.drawable.starhalfleft),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (i <= viewModel.uiState.commentRate) Primary else White)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.starhalfright),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (i <= viewModel.uiState.commentRate) Primary else White)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        TextField(
            value = viewModel.uiState.commentValue,
            onValueChange = { viewModel.setCommentValue(it) },
            modifier = Modifier.fillMaxWidth()
                .background(color = Background_Normal, shape = RoundedCornerShape(12.dp))
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp)),
            placeholder = { Text(text = "유적지에 대한 감상을 입력해주세요!") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Label, unfocusedTextColor = Label,
                focusedContainerColor = Fill_Normal,
                unfocusedContainerColor = Fill_Normal,
                disabledContainerColor = Fill_Normal,
                focusedIndicatorColor = Fill_Normal,
                unfocusedIndicatorColor = Fill_Normal,
                disabledIndicatorColor = Fill_Normal,
                unfocusedPlaceholderColor = Label,
                focusedPlaceholderColor = Label,
            )
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .border(1.dp, color = Blue_Netural, shape = RoundedCornerShape(12.dp))
                .clickable { viewModel.submitComment() }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = if (viewModel.uiState.commentLoading) {
                    "댓글 업로드 중입니다..."
                } else {
                    "작성 완료!"
                },
                color = Blue_Netural,
                style = AppTextStyles.Body1.bold
            )
        }
    }
}