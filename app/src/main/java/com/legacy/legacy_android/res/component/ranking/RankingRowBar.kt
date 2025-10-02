package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.res.component.title.SmallTitleBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural

@Composable
fun RankingRowBar(
    rank: Int?,
    blocks: Int?,
    name: String?,
    title: String?,
    level: Int?,
    img: String?
) {
    val normalized = blocks?.let { it % 1000 }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Background_Netural)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.width(32.dp), contentAlignment = Alignment.Center) {
            if (rank == null) {
                SkeletonBox(
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            } else {
                Text(
                    text = rank.toString(),
                    fontFamily = bitbit,
                    color = Label,
                    fontSize = 20.sp,
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        if (name == null || title == null || level == null || img == null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                SkeletonBox(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
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
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                AsyncImage(
                    model = img,
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.school_img),
                    error = painterResource(R.drawable.school_img)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = " $name",
                        color = Label,
                        style = AppTextStyles.Headline.bold
                    )
                    if (title.isNotEmpty()) {
                        SmallTitleBar(title = title)
                    }
                }
            }
        }
        Box(
            modifier = Modifier.width(80.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (blocks == null) {
                SkeletonBox(
                    modifier = Modifier
                        .width(60.dp)
                        .height(18.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            } else {
                Text(
                    text = "${blocks}블록",
                    fontFamily = bitbit,
                    fontSize = 16.sp,
                    color = if (blocks > 2000) Color(0xFFA05AE8) else when (normalized) {
                        in 0..199 -> (if (blocks < 1001) Color(0xFFA05AE8) else Color(0xFFEDB900)).copy(alpha = 0.6f)
                        in 200..399 -> Color(0xFFA05AE8).copy(alpha = 0.7f)
                        in 400..599 -> Color(0xFFA05AE8).copy(alpha = 0.8f)
                        in 600..799 -> Color(0xFFA05AE8).copy(alpha = 0.9f)
                        else -> Color(0xFFA05AE8).copy(alpha = 1.0f)
                    }
                )
            }
        }
    }
}

