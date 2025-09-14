package com.legacy.legacy_android.res.component.bars
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.legacy.legacy_android.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.data.user.clearToken
import com.legacy.legacy_android.res.component.bars.infobar.InfoBarViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun OptionBar(navHostController: NavHostController,
              setIsTabClicked: () -> Unit,
              onMailClick: (Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val viewModel: InfoBarViewModel = hiltViewModel()
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