package com.legacy.legacy_android.feature.screen.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Label_Alternative
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
            .imePadding()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(enabled = !isLoading.value) {
                        coroutineScope.launch {
                            isLoading.value = true
                            delay(100)
                            navHostController.popBackStack()
                            isLoading.value = false
                        }
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                )
                Text(
                    text = "목록으로",
                    style = AppTextStyles.Heading1.bold
                )
            }
            AsyncImage(
                model = viewModel.uiState.currentEvent?.eventImg,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(80.dp).background(Background_Alternative,
                    RoundedCornerShape(12.dp)
                )
            )
            Text(
                text = viewModel.uiState.currentEvent?.title ?: "이벤트 이름",
                style = AppTextStyles.Title2.bold,
            )

            Text(
                text = "${viewModel.uiState.currentEvent?.startAt} ~ ${viewModel.uiState.currentEvent?.endAt}",
                style = AppTextStyles.Caption1.regular,
                color = Label_Alternative
            )
            Text(
                text = viewModel.uiState.currentEvent?.description ?: "이벤트 이름",
                style = AppTextStyles.Title2.bold,
            )
        }
    }
}