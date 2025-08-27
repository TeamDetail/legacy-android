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
fun OptionBar(navHostController: NavHostController, setIsTabClicked: () -> Unit) {
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
                    viewModel.fetchMail()
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

    if (viewModel.isMailOpen) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2B2C).copy(alpha = 0.75f))
                .zIndex(5000f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {}
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.mail),
                            contentDescription = "mail",
                            modifier = Modifier.size(36.dp)
                        )
                        Text(text = "우편함", fontFamily = bitbit, color = White, fontSize = 24.sp)
                    }
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = White,
                        modifier = Modifier.clickable { viewModel.fetchMail() }
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "타이틀입니다.",
                        style = AppTextStyles.Body1.bold
                    )
                    Text(
                        text = "날짜입니다.",
                        style = AppTextStyles.Caption2.regular,
                        color = Label_Alternative
                    )
                    Spacer(Modifier.height(4.dp))
                    Row {
                        Box(
                            Modifier.size(40.dp)
                                .background(Fill_Normal, RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Line_Alternative,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Fill_Normal, RoundedCornerShape(12.dp))
                        .border(1.dp, Yellow_Netural, RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp, horizontal = 10.dp)
                        .clickable {
                        }
                ) {
                    Text("일괄 수령", color = Yellow_Netural, style = AppTextStyles.Caption1.Bold)
                }
            }
        }
    }

}