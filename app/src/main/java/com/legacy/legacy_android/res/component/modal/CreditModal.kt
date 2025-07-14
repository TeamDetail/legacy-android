package com.legacy.legacy_android.res.component.modal

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CreditModal(
    title: String,
    credit: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .zIndex(999f)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 27.dp, horizontal = 37.dp)
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = title,
                    style = AppTextStyles.Heading1.bold
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        append(
                            text = "${
                                NumberFormat.getNumberInstance(Locale.US).format(credit)
                            } 블록"
                        )
                        withStyle(style = SpanStyle(color = Yellow_Netural)) {
                            append("이 소모됩니다.")
                        }
                    },
                    style = AppTextStyles.Headline.medium,
                    color = Label_Alternative
                )
            }
                Row (
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .border(width = 4.dp, color = Fill_Normal, shape = RoundedCornerShape(12.dp))
                            .padding(4.dp)
                            .clickable {onDismiss}
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                .border(
                                    border = BorderStroke(1.dp, Red_Netural),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 40.dp)
                        ) {
                             Text(text = "취소", color = Red_Netural, style = AppTextStyles.Body1.bold)
                    }
                }
                    Box(
                        modifier = Modifier
                            .border(width = 4.dp, color = Fill_Normal, shape = RoundedCornerShape(12.dp))
                            .padding(4.dp)
                            .clickable {onConfirm}
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                .border(
                                    border = BorderStroke(1.dp, Blue_Netural),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 40.dp)
                        ) {
                            Text(text = "확인", color = Blue_Netural, style = AppTextStyles.Body1.bold)
                        }
                    }
            }
        }
    }
}