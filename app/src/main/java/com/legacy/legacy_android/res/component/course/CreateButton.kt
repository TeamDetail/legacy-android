package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White

@Composable
fun CreateButton(viewModel: CourseViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .zIndex(99f)
            .offset(
                x = screenWidth - 60.dp - 20.dp,
                y = screenHeight - 150.dp
            )
            .size(48.dp)
            .background(Primary, shape = RoundedCornerShape(160.dp))
            .clickable {
                viewModel.updateCourseStatus(CourseStatus.CREATE)
            }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Create,
            tint = White,
            modifier = Modifier.size(30.dp),
            contentDescription = "코스 생성"
        )
    }
}