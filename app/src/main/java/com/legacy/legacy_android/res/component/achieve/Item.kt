package com.legacy.legacy_android.res.component.achieve

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Strong
import com.legacy.legacy_android.ui.theme.Line_Alternative

@Composable
fun Item(
    count: Int
){
    Box(
        Modifier
            .size(40.dp)
            .background(Fill_Normal, RoundedCornerShape(8.dp))
            .border(
                1.dp,
                Line_Alternative,
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.cardpack),
            contentDescription = null,
            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(8.dp))
        )
        Box(
            Modifier.fillMaxSize().align(Alignment.BottomEnd).padding(4.dp)
        ){
            Text(
                text = if (count >= 100) "99+" else count.toString(),
                color = Label_Strong,
                style = AppTextStyles.Caption2.Bold.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}