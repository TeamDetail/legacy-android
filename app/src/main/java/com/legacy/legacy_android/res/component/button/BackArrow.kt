package com.legacy.legacy_android.res.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.Nav

@Composable
fun BackArrow(
    selectedId: Int,
    navHostController: NavHostController
){
    Image(
        painter = painterResource(R.drawable.arrow),
        contentDescription = null,
        modifier = Modifier
            .clickable {
                Nav.setNavStatus(selectedId)
                when (selectedId) {
                    0 -> navHostController.navigate("MARKET")
                    1 -> navHostController.navigate("ACHIEVE")
                    2 -> navHostController.navigate("HOME")
                    3 -> navHostController.navigate("TRIAL")
                    4 -> navHostController.navigate("RANKING")
                }
            }
    )
}