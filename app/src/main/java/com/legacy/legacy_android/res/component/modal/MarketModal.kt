package com.legacy.legacy_android.res.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Purple_Netural
import com.legacy.legacy_android.ui.theme.Purple_Normal
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MarketModal(
    credit: Int?,
    onConfirm: () -> Unit?,
    viewModel: MarketViewModel
) {
    val (soundPool, soundId) = RememberClickSound()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .zIndex(999f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 27.dp, horizontal = 37.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "정말 구매하시겠습니까?",
                    style = AppTextStyles.Headline.bold,
                    color = Label
                )
                Text(
                    text = buildAnnotatedString {
                        append(
                            text = "${
                                NumberFormat.getNumberInstance(Locale.US)
                                    .format(credit)
                            } 크레딧",
                        )
                        withStyle(
                            style = SpanStyle(
                                color = Yellow_Netural,
                            )
                        ) {
                            append("을 소모합니다.")
                        }
                    },
                    style = AppTextStyles.Body2.medium,
                )
            }
            Row (
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth(0.4f)
                        .border(1.dp, color = Purple_Normal, shape = RoundedCornerShape(8.dp))
                        .clickable(
                            onClick = {
                                onConfirm()
                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                            }
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp),
                        text = "구매",
                        color = Purple_Normal,
                        style = AppTextStyles.Caption1.Bold
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.4f)
                    .border(1.dp, color = Purple_Normal, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = {
                            onConfirm()
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                        }
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp),
                    text = "구매",
                    color = Purple_Normal,
                    style = AppTextStyles.Caption1.Bold
                )
            }
        }
    }
}