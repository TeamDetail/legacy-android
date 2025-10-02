@file:Suppress("DEPRECATION")

package com.legacy.legacy_android.res.component.modal

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.res.component.adventure.RuinImage
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OpenCardModal(viewModel: ProfileViewModel = hiltViewModel()) {
    val (soundPool, soundId) = RememberClickSound()
    val openCards = viewModel.uiState.openCardResponse ?: emptyList()
    val selectedItemName = viewModel.uiState.selectedItem?.itemName ?: ""
    val pagerState = rememberPagerState()

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
                    text = "카드 획득!!",
                    style = AppTextStyles.Title2.bold,
                )
                Text(
                    text = "~ $selectedItemName ~",
                    style = AppTextStyles.Caption1.Bold,
                    color = Label_Alternative
                )

                HorizontalPager(
                    count = openCards.size,
                    state = pagerState,
                    modifier = Modifier
                        .width(200.dp)
                        .height(280.dp)
                        .shadow(
                            elevation = 12.dp,
                            spotColor = Color(0x26FFFFFF),
                            ambientColor = Color(0x26FFFFFF)
                        )
                        .border(
                            width = 3.dp,
                            color = Fill_Normal,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(Fill_Normal, RoundedCornerShape(20.dp))
                ) { page ->
                    val card = openCards[page]

                    var isFlipped by remember { mutableStateOf(false) }

                    val rotation by animateFloatAsState(
                        targetValue = if (isFlipped) 180f else 0f,
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = FastOutSlowInEasing
                        ),
                        label = "card_flip"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                if (!isFlipped) {
                                    isFlipped = true
                                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                }
                            }
                            .graphicsLayer {
                                rotationY = rotation
                                cameraDistance = 12f * density
                            }
                    ) {
                        if (rotation <= 90f) {
                            Image(
                                painter = painterResource(R.drawable.unknown),
                                contentDescription = "카드 뒷면",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        rotationY = 180f
                                    }
                            ) {
                                RuinImage(
                                    image = card.cardImageUrl,
                                    height = 280,
                                    name = card.cardName,
                                    lineAttributeName = card.lineAttributeName,
                                    nationAttributeName = card.nationAttributeName,
                                    regionAttributeName = card.regionAttributeName,
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "${pagerState.currentPage + 1} / ${openCards.size}",
                    style = AppTextStyles.Caption1.Bold,
                    color = Label_Alternative
                )

                if (pagerState.currentPage == openCards.lastIndex && openCards.isNotEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Line_Alternative, RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.updateCardPackOpen(false)
                                viewModel.setSelectedItem(null)
                                viewModel.initCardPack()
                                viewModel.fetchMyInventory()
                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                            }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "획득 완료하고 닫기",
                            color = Label_Netural,
                            style = AppTextStyles.Caption1.Bold
                        )
                    }
                }
            }
        }
    }
}