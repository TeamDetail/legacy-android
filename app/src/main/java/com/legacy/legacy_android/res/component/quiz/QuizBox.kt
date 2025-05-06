package com.legacy.legacy_android.res.component.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.legacy.legacy_android.ui.theme.Background_Normal
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.Blue_Natural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Anternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Yellow

@Composable
fun QuizBox(
    name : String
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
            // Question 번호와 name
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "Q1",
                    color = Label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Text(
                    text = name,
                    color = Label_Anternative,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            // 저거 그 뭐더라 이름이랑 카드 나오는 거
            Column ( horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    modifier = Modifier,
                    text = "2024년 기준, 교장선생",
                    color = Label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Box(
                        modifier = Modifier
                            .border(border = BorderStroke(4.dp, Fill_Normal), shape = RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Fill_Normal)
                                .border(border = BorderStroke(1.dp, Line_Alternative))
                                .padding(vertical = 12.dp)
                        ) {
                            Text(
                                text = "앙기모띠",
                                color = Label,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .border(border = BorderStroke(4.dp, Fill_Normal))
                        .padding(4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Fill_Normal)
                            .border(border = BorderStroke(1.dp, Blue_Natural), shape = RoundedCornerShape(12.dp))
                            .padding(vertical = 4.dp, horizontal = 40.dp)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = buildAnnotatedString {
                                append("힌트 확인하기\n")
                                withStyle (style = SpanStyle(color = Yellow)) {
                                    append("(크레딧 3000필요)")
                                }
                            },
                            color = Blue_Natural,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}