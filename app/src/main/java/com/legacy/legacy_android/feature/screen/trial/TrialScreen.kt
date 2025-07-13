package com.legacy.legacy_android.feature.screen.trial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox

@Composable
fun TrialScreen(
    modifier: Modifier = Modifier,
    viewModel: TrialViewModel = hiltViewModel(),
    navHostController: NavHostController){
    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController
    ) {
        TitleBox(title = "시련", image = R.drawable.fight)
            }
        }