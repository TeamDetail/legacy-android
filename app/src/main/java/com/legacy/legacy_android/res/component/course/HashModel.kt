package com.legacy.legacy_android.res.component.course

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.White

@Composable
fun PlusButton(viewModel: CourseViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Fill_Normal, shape = RoundedCornerShape(4.dp))
            .height(32.dp)
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "태그 추가",
            tint = Label,
            modifier = Modifier.size(16.dp).clickable { viewModel.setIsHashTag() }
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun PlusInput(viewModel: CourseViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Background_Netural, RoundedCornerShape(4.dp))
            .width(100.dp)
            .height(32.dp)
            .padding(start = 8.dp)
    ) {
        Text(
            text = "#",
            style = AppTextStyles.Body2.medium.copy(color = White),
            modifier = Modifier.padding(end = 4.dp)
        )
        BasicTextField(
            value = viewModel.uiState.createCourseHashName,
            onValueChange = { viewModel.setCreateCourseHashName(it) },
            textStyle = AppTextStyles.Body2.medium.copy(color = Color.White),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (viewModel.uiState.createCourseHashName.isNotEmpty() && viewModel.uiState.createCourseHashName.length > 1)
                    viewModel.setCreateCourseHashTags(viewModel.uiState.createCourseHashName)
                    viewModel.setCreateCourseHashName("")
                    viewModel.setIsHashTag()
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (viewModel.uiState.createCourseHashName.isEmpty()) {
                        Text("태그 입력..", color = Color.White)
                    }
                    innerTextField()
                }
            }
        )
    }
}
