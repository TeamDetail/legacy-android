package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.White
import androidx.compose.ui.unit.sp

@Composable
fun SearchCourseBox(
    modifier: Modifier = Modifier,
    viewModel: CourseViewModel,
){
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(shape = RoundedCornerShape(12.dp), color = Background_Netural),
        value = viewModel.uiState.createRuinsName,
        shape = RoundedCornerShape(12.dp),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.searchRuins(viewModel.uiState.createRuinsName)
            }
        ),
        onValueChange = {
            viewModel.setCreateRuinsName(it)},
        placeholder = { Text(text = "유적지 이름으로 검색", color = White, fontSize = 12.sp,
            textAlign = TextAlign.Center) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "검색 아이콘",
                tint = White
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = White, unfocusedTextColor = White,
            focusedContainerColor = Background_Normal,
            unfocusedContainerColor = Background_Normal,
            disabledContainerColor = Background_Normal,
            focusedIndicatorColor = Background_Normal,
            unfocusedIndicatorColor = Background_Normal,
            disabledIndicatorColor = Background_Normal
        )
    )
}
