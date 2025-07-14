package com.legacy.legacy_android.res.component.quiz

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.legacy.legacy_android.ui.theme.Background_Normal
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Yellow

@Composable
fun QuizBox(
    name : String?,
    optionValue: List<String>?,
    quizStatus: MutableState<Int>,
    hintStatus: MutableState<Int>,
    ruinName: String,
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 27.dp, horizontal = 37.dp)
        ) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (quizStatus.value == index) Primary else Label,
                                    shape = RoundedCornerShape(100.dp),
                                )
                                .size(8.dp)
                        )
                    }
                }
            }
            // Question 번호와 name
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "Q${quizStatus.value + 1}",
                    style = AppTextStyles.Title1.bold
                )
                Text(
                    text = ruinName.toString(),
                    color = Label_Alternative,
                    style = AppTextStyles.Body1.medium
                )
            }
            // 저거 그 뭐더라 이름이랑 카드 나오는 거
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    modifier = Modifier,
                    text = name ?: "",
                    style = AppTextStyles.Title3.bold
                )
                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    optionValue?.forEach { option ->
                        Box(
                            modifier = Modifier
                                .border(
                                    border = BorderStroke(4.dp, Fill_Normal),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(4.dp)
                                .clickable {}
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Fill_Normal)
                                    .border(
                                        width = 1.dp,
                                        Line_Alternative,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(vertical = 12.dp)
                            ) {
                                Text(
                                    text = option,
                                    style = AppTextStyles.Body1.bold
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .border(width = 4.dp, color = Fill_Normal, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp)
                        .clickable{hintStatus.value = 1}
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                            .border(border = BorderStroke(1.dp, Blue_Netural), shape = RoundedCornerShape(12.dp))
                            .padding(vertical = 4.dp, horizontal = 40.dp)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = buildAnnotatedString {
                                append("힌트 확인하기\n")
                                withStyle(
                                style = AppTextStyles.Caption2.Medium.toSpanStyle().copy(color = Yellow)
                                ){
                                    append("(크레딧 3000필요)")
                                }
                            },
                            color = Blue_Netural,
                            style = AppTextStyles.Caption1.Bold
                        )
                    }
                }
            }
        }
    }
}