package com.legacy.legacy_android.res.component.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun NavBar(navHostController: NavHostController) {
    val navList = Nav.navList
    val selectedId = Nav.getNavStatus()
    val (soundPool, soundId) = RememberClickSound()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Background_Normal, shape = RoundedCornerShape(size = 20.dp))
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
                        .clickable {
                            if (selectedId != item.id) {
                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                Nav.setNavStatus(item.id)
                                navHostController.navigate(item.onClick.name)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp),
                        contentDescription = null,
                        painter = painterResource(
                            if (selectedId == item.id) item.selImage else item.image
                        )
                    )
                    Text(
                        text = item.name,
                        color = if (selectedId == item.id) Primary else Label_Strong,
                        style = AppTextStyles.Caption2.Medium
                    )
                }
            }
        }
    }
}
