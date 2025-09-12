package com.legacy.legacy_android.res.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BackButton(
    selectedId: Int,
    navHostController: NavHostController,
    title: String
) {
    val (soundPool, soundId) = RememberClickSound()
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = !isLoading.value) {
                coroutineScope.launch {
                    isLoading.value = true
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    delay(100)
                    Nav.setNavStatus(selectedId)
                    when (selectedId) {
                        0 -> navHostController.navigate("MARKET")
                        1 -> navHostController.navigate("ACHIEVE")
                        2 -> navHostController.navigate("HOME")
                        3 -> navHostController.navigate("COURSE")
                        4 -> navHostController.navigate("RANKING")
                    }
                }
            }
    ) {
        Image(
            painter = painterResource(R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier
        )
        Text(
            text = title,
            style = AppTextStyles.Heading1.bold
        )
    }
}