package com.legacy.legacy_android.res.component.bars.infobar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.bars.OptionBar
import com.legacy.legacy_android.ui.theme.*

import java.text.NumberFormat
import java.util.*

@Composable
fun InfoBar(
    navHostController: NavHostController
) {
    val viewModel: InfoBarViewModel = hiltViewModel()
    val profile by viewModel.profileFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearProfile()
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                if (profile == null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Label_Alternative.copy(alpha = 0.3f))
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(80.dp)
                                    .background(Label_Alternative.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .height(12.dp)
                                    .width(60.dp)
                                    .background(Label_Alternative.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .height(40.dp)
                            .fillMaxWidth(0.7f)
                            .background(Fill_Normal, RoundedCornerShape(12.dp))
                    )
            } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .fillMaxHeight()
                            .clickable { navHostController.navigate("profile") }
                    ) {
                        AsyncImage(
                            model = profile?.imageUrl,
                            contentDescription = "프로필 이미지",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp)),
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
                                style = AppTextStyles.Headline.bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
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
                            .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                            .fillMaxWidth(0.7f)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.coin),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = NumberFormat.getNumberInstance(Locale.US)
                                .format(profile?.credit ?: 0),
                            color = Yellow,
                            style = TextStyle(fontSize = 12.sp, fontFamily = bitbit)
                        )
                    }
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
                    OptionBar(navHostController, setIsTabClicked = {
                        viewModel.setIsTabClicked()
                    })
                }
            }
        }
    }
}
