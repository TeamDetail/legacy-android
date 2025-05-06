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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Anternative
import com.legacy.legacy_android.R
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Yellow
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard
import java.text.NumberFormat
import java.util.Locale

@Composable
fun InfoBar(
    name: String,
    level: Number,
    money: Number,
    isTabClicked: Boolean,
    setTabClicked: () -> Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
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
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                .fillMaxHeight()) {
                Image(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(R.drawable.temp_profile),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = name,
                        color = Label,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "LV. ${level.toString()}",
                        color = Label_Anternative,
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
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp)
                    .background(Fill_Normal, shape = RoundedCornerShape(size = 12.dp)),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.coin),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
                Text(
                    text = NumberFormat.getNumberInstance(Locale.US).format(money).toString(),
                    color = Yellow,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = bitbit,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .zIndex(10f)
                    .wrapContentSize(Alignment.TopEnd)
            ) {

                Image(
                    painter = painterResource(
                        if (isTabClicked) R.drawable.vector else R.drawable.tab
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(12.dp)
                        .background(Fill_Normal, shape = RoundedCornerShape(size = 12.dp))
                        .clickable { setTabClicked() }
                )
                if (isTabClicked) {
                    DropdownMenu (
                        expanded = isTabClicked,
                        onDismissRequest = { setTabClicked() },
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
}
