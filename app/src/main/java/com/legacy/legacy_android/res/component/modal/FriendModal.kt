package com.legacy.legacy_android.res.component.modal

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Purple_Normal
import com.legacy.legacy_android.ui.theme.Yellow_Netural

@Composable
fun FriendModal(
    friendName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    val (soundPool, soundId) = RememberClickSound()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black.copy(alpha = 0.75f))
            .zIndex(1500f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.8f)
                .padding(horizontal = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 28.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "정말 해당 친구와 헤어질까요?",
                    style = AppTextStyles.Heading1.bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$friendName 님과 친구를 삭제합니다.",
                    style = AppTextStyles.Caption1.Medium,
                    color = Yellow_Netural
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.8f)
                        .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Label_Assitive, RoundedCornerShape(8.dp))
                        .clickable {
                            onCancel()
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                        }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = "취소",
                        color = Label_Assitive,
                        style = AppTextStyles.Caption1.Bold
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.8f)
                        .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Purple_Normal, RoundedCornerShape(8.dp))
                        .clickable {
                            onConfirm()
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                        }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = "삭제",
                        color = Purple_Normal,
                        style = AppTextStyles.Caption1.Bold
                    )
                }
            }
        }
    }
}