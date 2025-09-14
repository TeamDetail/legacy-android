package com.legacy.legacy_android.res.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.modal.mail.MailModal
import com.legacy.legacy_android.ui.theme.Background_Alternative

@Composable
fun CommonScreenLayout(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    backgroundColor: androidx.compose.ui.graphics.Color = Background_Alternative,
    content: @Composable () -> Unit
) {
    var isMailModalVisible by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize().zIndex(99f)) {
        if (isMailModalVisible) {
            MailModal(onMailClick = {show -> isMailModalVisible = show })
        }
        // InfoBar
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .absoluteOffset(0.dp, 10.dp)
                .zIndex(5f)
        ) {
            InfoBar(
                navHostController,
                onMailClick = { show -> isMailModalVisible = show }
            )
        }

        // 콘텐츠
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(top = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = modifier
                    .height(100.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(horizontal = 12.dp)
            ) {
                content()
                Spacer(
                    modifier = modifier
                        .height(100.dp)
                )
            }
        }
        //navbar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 40.dp, horizontal = 12.dp)
                .zIndex(7f)
        ) {
            NavBar(navHostController = navHostController)
        }
    }
}