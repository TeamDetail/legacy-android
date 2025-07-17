package com.legacy.legacy_android.res.component.bars.infobar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.bars.OptionBar
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Yellow
import com.legacy.legacy_android.ui.theme.bitbit
import java.text.NumberFormat
import java.util.Locale

@Composable
fun InfoBar(
    navHostController: NavHostController
) {
    val (soundPool, soundId) = RememberClickSound()
    val viewModel: InfoBarViewModel = hiltViewModel()
    val profile by viewModel.profileFlow.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }
    Box(
        modifier = Modifier
            .absoluteOffset(0.dp, 30.dp)
            .fillMaxWidth(0.95f)
            .zIndex(999f)
            .height(70.dp)
            .background(Background_Normal, shape = RoundedCornerShape(size = 20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            // 프로필 & 코인
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                // 프로필
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.
                    fillMaxWidth(0.3f)
                        .fillMaxHeight()
                            .clickable { navHostController.navigate("profile") }
                ) {
                    AsyncImage(
                        model = profile?.imageUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.temp_profile),
                        error = painterResource(R.drawable.temp_profile)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = profile?.nickname.toString(),
                            style = AppTextStyles.Headline.bold
                        )
                        Text(
                            text = "LV. ${profile?.level}",
                            color = Label_Alternative,
                            style = AppTextStyles.Caption2.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth(0.3f),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.coin),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = NumberFormat.getNumberInstance(Locale.US)
                            .format(profile?.credit ?: 0),
                        color = Yellow,
                        style = TextStyle(fontSize = 16.sp, fontFamily = bitbit)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .zIndex(10f)
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                Image(
                    painter = painterResource(
                        if (viewModel.isTabClicked) R.drawable.vector else R.drawable.tab
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(12.dp)
                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                        .clickable { viewModel.setIsTabClicked() }
                )

                DropdownMenu(
                    expanded = viewModel.isTabClicked,
                    onDismissRequest = { viewModel.isTabClicked = false },
                    modifier = Modifier
                        .zIndex(11f)
                        .background(Background_Normal)
                ) {
                    OptionBar(navHostController)
                }
            }
        }
    }
}
