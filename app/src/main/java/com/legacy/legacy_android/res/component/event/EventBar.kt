package com.legacy.legacy_android.res.component.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.network.event.EventResponse
import com.legacy.legacy_android.feature.screen.event.EventViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun EventBar(
    data: EventResponse?,
    viewModel: EventViewModel,
    navHostController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(80.dp)
            .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                viewModel.getEventById(data?.eventId ?: 0,
                    { navHostController.navigate("course_info") })
            }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = data?.title ?: "이름이 없습니다.", style = AppTextStyles.Heading2.bold)
            Text(
                text = data?.shortDescription ?: "설명글이 없습니다.",
                style = AppTextStyles.Caption1.regular,
                color = Label_Alternative
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = data?.startAt ?: "시작일이 없습니다.",
                style = AppTextStyles.Caption1.regular,
                color = Label_Alternative
            )
            Text(
                text = ("~" + data?.endAt),
                style = AppTextStyles.Caption1.regular,
                color = Label_Alternative
            )
        }
    }
}