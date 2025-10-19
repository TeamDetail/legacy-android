package com.legacy.legacy_android.res.component.modal.check

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White
import java.time.LocalDate

@Composable
fun CheckModal(
    onCheckClick: (Boolean) -> Unit,
) {
    val today = LocalDate.now()
    val currentMonth by remember { mutableIntStateOf(today.monthValue) }
    val days = remember { (1..today.lengthOfMonth()).toList() }
    val checkList = remember { listOf("${currentMonth}월", "론칭") }
    val viewModel: CheckViewModel = hiltViewModel()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A2B2C).copy(alpha = 0.75f))
            .zIndex(5000f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {}
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(Background_Normal, RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            /** 헤더 */
            Header(onCheckClick)

            /** 선택 버튼 */
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                checkList.forEachIndexed { index, item ->
                    StatusButton(
                        selectedValue = viewModel.uiState.checkStatus,
                        onClick = { viewModel.changeCheckStatus(index) },
                        text = item,
                        id = index,
                        selectedColor = Primary,
                        nonSelectedColor = Line_Netural
                    )
                }
            }
            days.chunked(7).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowItems.forEach { day ->
                        val backgroundColor = when {
                            day < today.dayOfMonth -> Fill_Netural
                            day == today.dayOfMonth -> Primary
                            else -> Fill_Normal
                        }

                        val textColor = if (day < today.dayOfMonth) Label_Assitive else Label

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(backgroundColor, RoundedCornerShape(8.dp))
                                .border(1.dp, Line_Alternative, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                color = textColor,
                                style = AppTextStyles.Body2.bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(onCheckClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("출석체크", style = AppTextStyles.Heading2.bold)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = White,
            modifier = Modifier.clickable { onCheckClick(false) }
        )
    }
}