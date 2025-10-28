package com.legacy.legacy_android.res.component.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label_Strong
import com.legacy.legacy_android.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun NavBar(navHostController: NavHostController) {
    val navList = Nav.navList
    val (soundPool, soundId) = RememberClickSound()

    val selectedIdState = remember { mutableIntStateOf(Nav.getNavStatus()) }
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null ){println("ㅇㅇ")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            navList.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(
                            enabled = !isLoading.value,
                            indication = null,
                            interactionSource = null
                        ) {
                            if (selectedIdState.intValue != item.id) {
                                isLoading.value = true
                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                selectedIdState.intValue = item.id
                                Nav.setNavStatus(item.id)

                                coroutineScope.launch {
                                    navHostController.navigate(item.onClick.name) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    isLoading.value = false
                                }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        contentDescription = null,
                        painter = painterResource(
                            if (selectedIdState.intValue == item.id) item.selImage else item.image
                        )
                    )
                    Text(
                        text = item.name,
                        color = if (selectedIdState.intValue == item.id) Primary else Label_Strong,
                        style = AppTextStyles.Caption2.Medium
                    )
                }
            }
        }
    }
}
