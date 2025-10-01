package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Primary_Alternative

@Composable
fun TitleSelector(
    count: Int,
    max: Int,
    onClick: (Int) -> Unit,
    id: Int,
    selectedValue: Int,
    text: String,
    modifier: Modifier
){
    val isSelected = selectedValue == id
    Box(
        modifier.fillMaxWidth()
            .clickable{ onClick(id)
            }
            .background(
                color = if (isSelected) Primary else Background_Netural,
                shape = RoundedCornerShape(8.dp)
            )
    ){
        Row (
            modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Label,
                style = AppTextStyles.Body2.bold
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = if (isSelected) Primary_Alternative else Background_Netural,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$count / $max",
                    color = Label_Netural,
                    style = AppTextStyles.Caption2.Medium
                )
            }
        }
    }
}