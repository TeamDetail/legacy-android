package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.network.achieve.CardPack
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles

@Composable
fun PackWrap(
    newList: List<CardPack>?,
    viewModel: MarketViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column {
            Text(
                text = "시대",
                style = AppTextStyles.Heading2.bold
            )
            when {
                newList == null -> {
                    repeat(3) {
                        SkeletonBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                else -> {
                    newList.filter { it.storeSubType != "LINE" && it.storeSubType != "REGION" }
                        .forEach { pack ->
                            Pack(cardPack = pack, viewModel = viewModel)
                        }
                }
            }
        }

        Column {
            Text(
                text = "계열",
                style = AppTextStyles.Heading2.bold
            )
            when {
                newList == null -> {
                    repeat(2) {
                        SkeletonBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                else -> {
                    newList.filter { it.storeSubType == "LINE" }.forEach { pack ->
                        Pack(cardPack = pack, viewModel = viewModel)
                    }
                }
            }
        }

        Column {
            Text(
                text = "지역",
                style = AppTextStyles.Heading2.bold
            )
            when {
                newList == null -> {
                    repeat(2) {
                        SkeletonBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                else -> {
                    newList.filter { it.storeSubType == "REGION" }.forEach { pack ->
                        Pack(cardPack = pack, viewModel = viewModel)
                    }
                }
            }
        }
    }
}