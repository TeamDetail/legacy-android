package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural

data class StatTableResponse(
    val title: String,
    val value: String?
)

@Composable
fun StatTable(
    data: List<StatTableResponse>
){
    Column (
        modifier = Modifier.fillMaxWidth().background(color = Fill_Normal, shape = RoundedCornerShape(16.dp)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        data.forEach { it ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = it.title,
                    style = AppTextStyles.Body1.medium,
                    color = Label_Alternative
                )
                Spacer(modifier = Modifier.width(8.dp))
                Spacer(
                    modifier = Modifier
                        .weight(0.3f)
                        .height(1.dp)
                        .background(Line_Netural)
                )
                Spacer(modifier = Modifier.width(8.dp))
                it.value?.let { text ->
                    Text(
                        text = text,
                        style = AppTextStyles.Body1.bold,
                    )
                }
            }
        }
    }
}