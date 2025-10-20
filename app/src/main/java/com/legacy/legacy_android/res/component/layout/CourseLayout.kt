package com.legacy.legacy_android.res.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.modal.check.CheckModal
import com.legacy.legacy_android.res.component.modal.mail.MailModal
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White

@Composable
fun CourseScreenLayout(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    backgroundColor: Color = Background_Alternative,
    content: @Composable () -> Unit
) {
    var isMailModalVisible by remember { mutableStateOf(false) }
    var isCheckModalVisible by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize().zIndex(99f)) {
        if (isMailModalVisible) {
            MailModal(onMailClick = {show -> isMailModalVisible = show })
        }
        if (isCheckModalVisible){
            CheckModal(onCheckClick = { show -> isCheckModalVisible = show})
        }
        IconButton(
            onClick = {
                navHostController.navigate("CREATE_COURSE") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 120.dp)
                .zIndex(8f)
                .size(48.dp)
                .background(Primary, shape = CircleShape)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){}
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "만들기",
                tint = White
            )
        }
        // InfoBar
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .absoluteOffset(0.dp, 10.dp)
                .zIndex(5f)
        ) {
            InfoBar(navHostController, onMailClick = { show -> isMailModalVisible = show }, onCheckClick = {show -> isCheckModalVisible = show })
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