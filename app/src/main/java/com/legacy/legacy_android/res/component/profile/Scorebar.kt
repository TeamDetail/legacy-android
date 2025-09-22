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
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative

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
            style = AppTextStyles.Label.Bold,
            color = Label_Alternative,
        )
        Row (
            modifier = modifier
                .fillMaxWidth()
                .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
        ){
            Text(
                text = text,
                style = AppTextStyles.Body1.bold,
                modifier = modifier
                    .padding(12.dp, 10.dp, 5.dp, 10.dp)
            )
        }
    }
}