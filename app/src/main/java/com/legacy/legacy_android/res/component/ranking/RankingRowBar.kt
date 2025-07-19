package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.res.component.title.SmallTitleBar
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun RankingRowBar(
    rank : Int?,
    blocks : Int?,
    name : String?,
    title : String?,
    level: Int?
){
    val normalized = blocks?.let { it % 1000 }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Background_Netural),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        if (rank == null || blocks == null || name == null || title == null || level == null) {
            SkeletonBox(
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                SkeletonBox(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape)
                )
                Column (
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .width(144.dp)
                ){
                    SkeletonBox(
                        modifier = Modifier
                            .width(120.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    SkeletonBox(
                        modifier = Modifier
                            .width(80.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            SkeletonBox(
                modifier = Modifier
                    .width(60.dp)
                    .height(18.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        } else {
            Text(
                text = rank.toString(),
                fontFamily = bitbit,
                color = Label,
                fontSize = 28.sp,
            )
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Image(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    painter = painterResource(R.drawable.temp_profile),
                    contentDescription = null
                )
                Column (
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .width(144.dp)
                ){
                    Text(
                        text = buildAnnotatedString {
                            append(name)
                            withStyle(style = SpanStyle(color = Label_Alternative, fontSize = 12.sp)) {
                                append("  LV. ${level}")
                            }
                        },
                        color = Label,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    SmallTitleBar(title = title)
                }
            }
            Text(
                text = "${blocks}블록",
                fontFamily = bitbit,
                fontSize = 18.sp,
                color = if (blocks > 2000) Color(0xFFA05AE8) else when (normalized) {
                    in 0..199   -> (if (blocks < 1001) Color(0xFFA05AE8) else Color(0xFFEDB900)).copy(alpha = 0.6f)
                    in 200..399 -> Color(0xFFA05AE8).copy(alpha = 0.7f)
                    in 400..599 -> Color(0xFFA05AE8).copy(alpha = 0.8f)
                    in 600..799 -> Color(0xFFA05AE8).copy(alpha = 0.9f)
                    else        -> Color(0xFFA05AE8).copy(alpha = 1.0f)
                }
            )
        }
    }
}
