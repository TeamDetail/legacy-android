package com.legacy.legacy_android.res.component.title

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Netural
import com.legacy.legacy_android.ui.theme.Yellow_Netural

@Composable
fun TitleBar(title: String) {
    Row(
        modifier = Modifier
            .border(1.dp, Yellow_Netural, shape = RoundedCornerShape(8.dp))
            .padding(1.dp)
    ) {
        Row(
            modifier = Modifier
                .border(1.dp, Fill_Netural, shape = RoundedCornerShape(6.dp))
                .background(Fill_Netural)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(6.dp),
                text = title,
                style = AppTextStyles.Caption2.Bold,
                color = Yellow_Netural,
            )
        }
    }
}
