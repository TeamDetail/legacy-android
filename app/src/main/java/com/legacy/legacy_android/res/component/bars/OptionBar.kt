package com.legacy.legacy_android.res.component.bars
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.legacy.legacy_android.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.data.user.clearToken
import com.legacy.legacy_android.ui.theme.Background_Normal

@Composable
fun OptionBar(navHostController: NavHostController,
              setIsTabClicked: () -> Unit,
              onMailClick: (Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val iconModifier = Modifier.size(30.dp)
            Image(painter = painterResource(R.drawable.friends), contentDescription = null,
                modifier = iconModifier.clickable {
                    setIsTabClicked()
                navHostController.navigate("friend")
            })
            Image(
                painter = painterResource(R.drawable.mail),
                contentDescription = null,
                modifier = iconModifier.clickable {
                    setIsTabClicked()
                    onMailClick(true)
                }
            )
            Image(painter = painterResource(R.drawable.setting), contentDescription = null,
                modifier = iconModifier.clickable {
                    setIsTabClicked()
                    navHostController.navigate("setting")
                })
            Image(
                painter = painterResource(R.drawable.info),
                contentDescription = null,
                modifier = iconModifier.clickable {
                    setIsTabClicked()
                }
            )
            Image(
                painter = painterResource(R.drawable.logout),
                contentDescription = null,
                modifier = iconModifier.clickable {
                    setIsTabClicked()
                    coroutineScope.launch {
                        clearToken(context)
                        navHostController.navigate("login") {
                        }
                    }
                }
            )
        }
    }
}