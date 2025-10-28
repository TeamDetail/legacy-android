package com.legacy.legacy_android.res.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles

@Composable
fun LoginButton(
    onClick: () -> Unit,
    icon: Painter,
    name: String,
    color: Color,
    bgColor: Color,
    viewModel: LoginViewModel
) {
    Box(
        modifier = Modifier
            .width(321.dp)
            .height(54.dp)
            .background(bgColor, shape = RoundedCornerShape(16.dp))
            .clickable (
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (!viewModel.loadingState.value) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$name 로그인",
                    style = AppTextStyles.Body2.medium.merge(
                        TextStyle(
                            color = color,
                            textAlign = TextAlign.Center,
                        )
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.width(20.dp))
            }
        }else{
            Text("로그인 중입니다...",
                style = AppTextStyles.Body2.medium.merge(
                    TextStyle(
                        color = color,
                        textAlign = TextAlign.Center,
                    )
                )
            )
        }
    }
}