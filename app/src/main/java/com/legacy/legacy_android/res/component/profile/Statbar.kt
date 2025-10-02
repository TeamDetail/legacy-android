package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun Statbar(
    modifier: Modifier,
    percent: Int,
    text: String,
    subtext: String = "",
    barColor: Color,
    textStyle: TextStyle = AppTextStyles.Headline.bold
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = modifier
                .matchParentSize()
                .background(Fill_Normal)
        )
        Box(
            modifier = modifier
                .fillMaxWidth(percent.toFloat() / 100f)
                .fillMaxHeight()
                .background(color = barColor)
        )
        Box(
            modifier = modifier
                .matchParentSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                buildAnnotatedString {
                    append(text)
                    withStyle(
                        style = AppTextStyles.Caption1.Bold.toSpanStyle().copy(color = Label_Alternative)
                    ) {
                        append(subtext)
                    }
                },
                style = textStyle
            )
        }
    }
}