package com.legacy.legacy_android.res.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.ui.theme.AppTextStyles

@Composable
fun BackButton(
    selectedId: Int,
    navHostController: NavHostController,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackArrow(navHostController = navHostController, selectedId = selectedId)
        Text(
            text = title,
            style = AppTextStyles.Heading1.bold
        )
    }
}