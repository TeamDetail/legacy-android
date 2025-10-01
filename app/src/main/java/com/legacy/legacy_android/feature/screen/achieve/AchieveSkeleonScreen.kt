package com.legacy.legacy_android.feature.screen.achieve

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun AchieveBoxSkeleton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 이미지 자리
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Label_Alternative.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // 제목 + 타입
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .width(140.dp)
                    .background(Label_Alternative.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            )
            // 내용
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(200.dp)
                    .background(Label_Alternative.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            )
            // 진행도 2개
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(80.dp)
                        .background(Label_Alternative.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(100.dp)
                        .background(Label_Alternative.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                )
            }
        }
    }
}
