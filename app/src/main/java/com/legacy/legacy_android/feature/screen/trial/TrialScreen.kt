package com.legacy.legacy_android.feature.screen.trial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Background_Alternative

@Composable
fun TrialScreen(
    modifier: Modifier = Modifier,
    viewModel: TrialViewModel = hiltViewModel(),
    navHostController: NavHostController){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Background_Alternative)
    ) {
        InfoBar(navHostController)
        Column(
            modifier = modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = modifier
                    .height(70.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleBox(title = "시련", image = R.drawable.fight)
            }
        }
            Box(
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
                    .zIndex(7f)
            ) { NavBar(navHostController = navHostController)
            }
    }
}