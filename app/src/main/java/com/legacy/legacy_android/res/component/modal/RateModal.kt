package com.legacy.legacy_android.res.component.modal

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Purple_Normal
import com.legacy.legacy_android.ui.theme.White

@Composable
fun RateModal(
    viewModel: HomeViewModel
) {
    var tempRate by remember { mutableStateOf(viewModel.uiState.commentRate) }
    val (soundPool, soundId) = RememberClickSound()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black.copy(alpha = 0.75f))
            .zIndex(1500f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 28.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tempRate.toString(),
                    style = AppTextStyles.Title2.bold,
                    color = Primary
                )
                Row (){
                    for (i in 1..10) {
                        if (i % 2 != 0) {
                            Image(
                                painter = painterResource(R.drawable.starhalfleft),
                                contentDescription = null,
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    tempRate = i
                                },
                                colorFilter = ColorFilter.tint(if (i <= tempRate) Primary else White)
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.starhalfright),
                                contentDescription = null,
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    tempRate = i
                                },
                                colorFilter = ColorFilter.tint(if (i <= tempRate) Primary else White)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }

                Text(text = "클릭해서 별점 선택..", color = Label_Alternative, style = AppTextStyles.Caption1.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Label_Assitive, RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.updateCommentModal(false)
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
                            .weight(1f)
                            .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Purple_Normal, RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.updateCommentRate(tempRate)
                                viewModel.updateCommentModal(false)
                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                            }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = "확인",
                            color = Purple_Normal,
                            style = AppTextStyles.Caption1.Bold
                        )
                    }
                }
            }
        }
    }
}