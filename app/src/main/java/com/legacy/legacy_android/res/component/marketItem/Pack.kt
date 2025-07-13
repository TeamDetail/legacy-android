package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard
import java.text.NumberFormat
import java.util.Locale

@Composable
fun Pack(
    packModel: PackModel){
    Box(
        modifier = Modifier
            .background(Fill_Normal, RoundedCornerShape(20.dp))
    ){
        Box(
            modifier = Modifier
                .width(189.5.dp)
                .border(1.dp, packModel.border, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(Fill_Normal)
        ){
            Column (
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = packModel.name,
                        fontFamily = bitbit,
                        color = Label,
                        fontSize = 20.sp
                    )
                    Text(
                        text = packModel.description,
                        fontFamily = pretendard,
                        style = AppTextStyles.Caption1.ExtraBold,
                        color = Label_Alternative
                    )
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        append(NumberFormat.getNumberInstance(Locale.US).format(packModel.credit).toString())
                        withStyle (style = SpanStyle(color = Label)) {
                            append(" 크레딧")
                        }
                    },
                    color = Yellow_Netural,
                    fontFamily = bitbit,
                    fontSize = 16.sp
                )
            }
        }
    }
}