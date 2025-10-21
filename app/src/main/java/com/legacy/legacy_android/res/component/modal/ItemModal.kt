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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Purple_Normal

@Composable
fun ItemModal(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val (soundPool, soundId) = RememberClickSound()
    LaunchedEffect(Unit) {
        viewModel.initCardPackOpenCount()
    }
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
                Text(text = "사용할 개수 선택", style = AppTextStyles.Heading1.bold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.background(
                            color = Fill_Normal,
                            shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "down",
                            tint = Label,
                            modifier = Modifier
                                .clickable { viewModel.decreasePackOpenCount(1) }
                                .size(40.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(
                            color = Fill_Normal,
                            shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                            text = viewModel.uiState.packOpenCount.toString(),
                            style = AppTextStyles.Title1.bold
                        )
                    }
                    Box(
                        modifier = Modifier.background(
                            color = Fill_Normal,
                            shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "up",
                            tint = Label,
                            modifier = Modifier
                                .clickable { viewModel.increasePackOpenCount(1) }
                                .size(40.dp)
                        )
                    }
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
                                viewModel.updateCardPackOpen(false)
                                viewModel.updateCreditPackOpen(false)
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
                                viewModel.updateCardPackOpen(false)
                                viewModel.updateCreditPackOpen(false)
                                if (viewModel.uiState.selectedItem?.itemType == "CARD_PACK") {
                                    viewModel.openCardPack()
                                } else {
                                    viewModel.openCreditPack()
                                }
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