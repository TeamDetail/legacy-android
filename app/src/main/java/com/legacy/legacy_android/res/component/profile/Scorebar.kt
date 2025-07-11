package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun Scorebar(
    modifier: Modifier,
    title: String,
    text: String
){
    Column (
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            color = Label_Alternative,
            fontWeight = FontWeight.Bold,
            fontFamily = pretendard
        )
        Row (
            modifier = modifier
                .fillMaxWidth()
                .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
        ){
            Text(
                text = text,
                fontSize = 16.sp,
                color = Label,
                fontWeight = FontWeight.Bold,
                fontFamily = pretendard,
                modifier = modifier
                    .padding(5.dp, 10.dp, 5.dp, 10.dp)
            )
        }
    }
}