package com.legacy.legacy_android.res.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    borderColor: Color = Blue_Netural,
    textColor: Color = Label,
    backgroundColor: Color = Fill_Normal,
    outerBorderColor: Color = Fill_Normal,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    fontSize: TextUnit = 16.sp,
    cornerRadius: Int = 12,
    textStyle: TextStyle = AppTextStyles.Body1.bold
) {
    // 바깥 테두리
    Box(
        modifier = modifier
            .background(outerBorderColor, shape = RoundedCornerShape(cornerRadius.dp))
            .border(2.dp, outerBorderColor, RoundedCornerShape(cornerRadius.dp))
            .padding(2.dp)
    ) {
        // 안쪽 버튼
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(cornerRadius.dp))
                .border(1.dp, color = borderColor, shape = RoundedCornerShape(cornerRadius.dp))
                .clickable { onClick() }
                .padding(contentPadding)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = textColor,
                style = textStyle.merge(TextStyle(fontSize = fontSize))
            )
        }
    }
}
