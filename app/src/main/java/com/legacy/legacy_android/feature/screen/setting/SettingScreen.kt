package com.legacy.legacy_android.feature.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.res.component.button.BackButton
import com.legacy.legacy_android.res.component.friend.FriendBar
import com.legacy.legacy_android.ui.theme.AppTextStyles

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    val selectedId = Nav.getNavStatus()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            BackButton(title = "설정", navHostController = navHostController, selectedId = selectedId)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "사운드",
                    style = AppTextStyles.Heading2.bold,
                )

            }
        }
    }
}
