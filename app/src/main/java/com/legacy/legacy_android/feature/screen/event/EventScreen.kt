package com.legacy.legacy_android.feature.screen.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.event.EventBar
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.getAllEvents()
    }
    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewModel.uiState.eventList.forEach { t->
                EventBar(t, viewModel, navHostController)
            }
        }
    }
}