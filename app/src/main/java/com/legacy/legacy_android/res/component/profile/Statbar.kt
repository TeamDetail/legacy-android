package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun Statbar(
    modifier: Modifier,
    percent : Float,
    name: String,
    text: String,
    subtext: String,
    barColor: Color
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            color = Label
        )

        Box(
            modifier = modifier
                .fillMaxWidth(0.9f)
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
                    .fillMaxWidth(percent)
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
                        append("Lv. ${text} ")
                        withStyle(
                            style = SpanStyle(color = Label_Alternative, fontSize = 16.sp)
                        ) {
                            append(subtext)
                        }
                    },
                    color = Label,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                    fontSize = 20.sp
                )
            }
        }
    }
}