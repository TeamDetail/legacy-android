package com.legacy.legacy_android.res.component.title

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Label_Strong
import com.legacy.legacy_android.ui.theme.Primary

@Composable
fun TitleBar(title: String, modifier: Modifier = Modifier) {
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

            drawPath(path = path, color = Primary)
        }

        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = AppTextStyles.Label.ExtraBold.fontWeight,
            color = Label_Strong,
        )
    }
}