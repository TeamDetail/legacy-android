package com.legacy.legacy_android.res.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Green_Normal
import com.legacy.legacy_android.ui.theme.Red_Normal

@Composable
fun AlertModal( modifier: Modifier, isCorrect: Boolean, correctMessage: String, incorrectMessage: String){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(Fill_Normal, RoundedCornerShape(16.dp)).height(48.dp)
            .padding(horizontal = 16.dp).zIndex(999f)
    ){
        if (!isCorrect) {
            Icon(
                imageVector = Icons.Default.Close,
                tint = Red_Normal,
                contentDescription = "닫기",
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = incorrectMessage,
                color = Red_Normal,
                style = AppTextStyles.Body1.bold
            )
        }else{
            Icon(
                imageVector = Icons.Default.Done,
                tint = Green_Normal,
                contentDescription = "성공",
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = correctMessage,
                color = Green_Normal,
                style = AppTextStyles.Body1.bold
            )
        }
    }
}