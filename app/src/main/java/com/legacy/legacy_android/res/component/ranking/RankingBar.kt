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
import com.legacy.legacy_android.res.component.title.SmallTitleBar
import com.legacy.legacy_android.ui.theme.Blue_Natural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun RankingBar(
    rank : Int,
    blocks : Int,
    name : String,
    title : String,
    zIndex: Float,
) {
    val normalized = blocks % 1000
    Box(
        modifier = Modifier
            .border(2.dp, Background_Normal, shape = RoundedCornerShape(20.dp))
            .padding(2.dp)
            .width(100.dp)
            .zIndex(zIndex)
    ) {
        Box(
            modifier = Modifier
                .background(color = Background_Normal, shape = RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp, color = if (rank == 1) {
                        Primary
                    } else if (rank == 2) {
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
                            text = "${rank}위",
                            color = if (rank == 1) {
                                Primary
                            } else if (rank == 2) {
                                Red_Netural
                            } else {
                                Blue_Natural
                            },
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "${blocks}블록",
                            color = if (blocks > 2000) Color(0xFFA05AE8) else when (normalized) {
                                in 0..199   -> (if (blocks < 1001) Color(0xFFA05AE8) else Color(0xFFEDB900)).copy(alpha = 0.6f)
                                in 200..399 -> Color(0xFFA05AE8).copy(alpha = 0.7f)
                                in 400..599 -> Color(0xFFA05AE8).copy(alpha = 0.8f)
                                in 600..799 -> Color(0xFFA05AE8).copy(alpha = 0.9f)
                                else        -> Color(0xFFA05AE8).copy(alpha = 1.0f)
                            },
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
                SmallTitleBar(title = title)
            }
        }
    }
}