package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.ui.theme.Blue_Natural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.Yellow
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun RankingBar(
    grade : Int,
    rank : Int,
    name : String,
    title : String,
    zIndex: Float,
) {
    Box(
        modifier = Modifier
            .border(2.dp, Background_Normal, shape = RoundedCornerShape(20.dp))
            .padding(2.dp)
            .zIndex(zIndex)
    ) {
        Box(
            modifier = Modifier
                .background(color = Background_Normal, shape = RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp, color = if (grade == 1) {
                        Primary
                    } else if (grade == 2) {
                        Red_Netural
                    } else {
                        Blue_Natural
                    }, shape = RoundedCornerShape(20.dp)
                )

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 200.dp, start = 16.dp, end = 16.dp)
                    .shadow(
                        elevation = 12.dp,
                        spotColor = Color(0x66111212),
                        ambientColor = Color(0x66111212)
                    )
                    .fillMaxHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(30.dp)),
                        painter = painterResource(R.drawable.temp_profile),
                        contentDescription = null
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${grade}위",
                            color = if (grade == 1) {
                                Primary
                            } else if (grade == 2) {
                                Red_Netural
                            } else {
                                Blue_Natural
                            },
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "${rank}블록",
                            color = Yellow_Netural,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = name,
                            color = Label,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .border(width = 1.dp, color = Yellow, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = title,
                        color = Yellow,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .padding(vertical = 2.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }
}