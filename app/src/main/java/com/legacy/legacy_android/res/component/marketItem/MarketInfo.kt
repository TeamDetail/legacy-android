package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Fill_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Purple_Netural
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun MarketInfo(
    quantity: Int,
    magnification: Double,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Fill_Normal, shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 8.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    append("오늘 총 ")
                    withStyle(style = SpanStyle(color = Yellow_Netural)) {
                        append(quantity.toString())
                    }
                    append("개 구매!")
                },
                color = Label,
                fontWeight = FontWeight.Bold,
                fontFamily = pretendard,
                fontSize = 22.sp
            )
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    append("현재 가격 배율\n")
                    withStyle(style = SpanStyle(color = Yellow_Netural, fontSize = 28.sp)) {
                        append("${magnification}배")
                    }
                },
                color = Label,
                fontWeight = FontWeight.Bold,
                fontFamily = pretendard,
                fontSize = 12.sp
            )
            Text(
                text = "카드팩은 하루 동안, \n구매 시마다 가격이 상승합니다.",
                color = Label_Alternative,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Fill_Netural, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = buildAnnotatedString {
                        append("초기화까지 ")
                        withStyle(style = SpanStyle(color = Purple_Netural )) {
                            append(time.toString())
                        }
                    },
                    color = Label_Netural,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}
