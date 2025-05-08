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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.bars.OptionBar
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Yellow
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard
import java.text.NumberFormat
import java.util.Locale

@Composable
fun InfoBar(

) {
    val viewModel: InfoBarViewModel = hiltViewModel();
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
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(999.dp)),
                    painter = painterResource(R.drawable.temp_profile),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = viewModel.name,
                        color = Label,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "LV. ${viewModel.level}",
                        color = Label_Alternative,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .background(Fill_Normal, shape = RoundedCornerShape(12.dp)),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.coin),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = NumberFormat.getNumberInstance(Locale.US).format(viewModel.money),
                    color = Yellow,
                    style = TextStyle(fontSize = 14.sp, fontFamily = bitbit)
                )
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
                    OptionBar()
                }
            }
        }
    }
}
