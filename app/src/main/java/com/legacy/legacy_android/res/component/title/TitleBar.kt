package com.legacy.legacy_android.res.component.title

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Label_Strong

@Composable
fun TitleBar(title: String, modifier: Modifier = Modifier, styleId: Int) {
    val backgroundColor = when (styleId) {
        1 -> Color(0xFF9B6CFF) // 보라색 (BETA TESTER / SUPPORTER)
        2 -> Color(0xFFEB6E61) // 주황빛 레드 (RANKER / CHAMPION)
        3 -> Color(0xFFEFC44A) // 노란색 (완벽주의자 / 전국일주)
        4 -> Color(0xFF6DA8E5) // 하늘색 (고독한 탐험가 / 서울 / 대구)
        5 -> Color(0xFF4D7A4E) // 초록색 (무과금 / 지갑전사)
        else -> Color(0xFF9B6CFF) // 기본값 (보라)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val cutWidth = 10.dp.toPx()

            val path = Path().apply {
                moveTo(cutWidth, 0f)
                lineTo(size.width - cutWidth, 0f)
                lineTo(size.width, size.height / 2f)
                lineTo(size.width - cutWidth, size.height)
                lineTo(cutWidth, size.height)
                lineTo(0f, size.height / 2f)
                close()
            }
            drawPath(path = path, color = backgroundColor)
        }

        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = AppTextStyles.Label.ExtraBold.fontWeight,
            color = Label_Strong,
        )
    }
}
