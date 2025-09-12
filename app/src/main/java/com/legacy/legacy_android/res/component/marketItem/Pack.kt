package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.feature.network.achieve.CardPack
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard
import java.text.NumberFormat
import java.util.Locale

@Composable
fun Pack(
    cardPack: CardPack,
    viewModel: MarketViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Box(
                modifier = Modifier.size(100.dp)
                    .background(color = Fill_Normal, shape = RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardPack.cardpackName,
                    fontFamily = bitbit,
                    color = Label,
                    fontSize = 20.sp
                )
                Text(
                    text = cardPack.cardpackContent,
                    fontFamily = pretendard,
                    style = AppTextStyles.Caption2.Medium,
                    color = Label_Alternative
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .border(1.dp, color = Line_Alternative, shape = RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.setModal()
                            viewModel.setCardPack(cardPack)
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center,
                        text = buildAnnotatedString {
                            append(
                                NumberFormat.getNumberInstance(Locale.US).format(cardPack.price)
                                    .toString()
                            )
                            withStyle(style = SpanStyle(color = Label)) {
                                append(" 크레딧")
                            }
                        },
                        style = AppTextStyles.Caption2.Bold,
                        color = Label_Netural
                    )
                }
            }
        }
    }
}