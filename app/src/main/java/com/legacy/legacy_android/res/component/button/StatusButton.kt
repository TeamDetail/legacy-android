package com.legacy.legacy_android.res.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Assitive
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun StatusButton(
    id: Int,
    selectedValue: Int,
    onClick: () -> Unit,
    text: String,
    selectedColor : Color,
    nonSelectedColor : Color
) {
    val isSelected = selectedValue == id

    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) selectedColor else nonSelectedColor,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = if (isSelected) Label else Label_Assitive,
            fontSize = 13.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 12.dp)
        )
    }
}
