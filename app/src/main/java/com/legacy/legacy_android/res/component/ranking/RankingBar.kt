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
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.pretendard
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RankingBar(
    rank : Int?,
    blocks : Int?,
    name : String?,
    title : String?,
    zIndex: Float,
) {
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
                    width = 1.dp, color = when (rank) {
                        1 -> {
                            Primary
                        }
                        2 -> {
                            Red_Netural
                        }
                        else -> {
                            Blue_Netural
                        }
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
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    if (rank == null || blocks == null || name == null || title == null) {
                        SkeletonBox(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SkeletonBox(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            SkeletonBox(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(15.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            SkeletonBox(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(16.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        }
                        SkeletonBox(
                            modifier = Modifier
                                .width(80.dp)
                                .height(24.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
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
                                color = when (rank) {
                                    1 -> {
                                        Primary
                                    }
                                    2 -> {
                                        Red_Netural
                                    }
                                    else -> {
                                        Blue_Netural
                                    }
                                },
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "${blocks}블록",
                                color = Yellow_Netural,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = name,
                                color = Label,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        if (title != "")SmallTitleBar(title = title)
                    }
                }
            }
        }
    }
}