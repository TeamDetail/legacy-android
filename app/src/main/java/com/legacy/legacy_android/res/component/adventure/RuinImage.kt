package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun RuinImage(
    image: String?,
    name: String?,
    nationAttributeName: String?,
    regionAttributeName: String?,
    lineAttributeName: String?,
    height: Int,
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .border(
                2.dp,
                color = Line_Netural,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        if (isLoading) {
            // 스켈레톤 상태
            SkeletonBox(
                modifier = Modifier.matchParentSize()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Background_Normal.copy(alpha = 0.3f))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SkeletonBox(
                    modifier = Modifier
                        .width(60.dp)
                        .height(20.dp)
                )
                SkeletonBox(
                    modifier = Modifier
                        .width(70.dp)
                        .height(20.dp)
                )
                SkeletonBox(
                    modifier = Modifier
                        .width(80.dp)
                        .height(20.dp)
                )
            }

            SkeletonBox(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .width(120.dp)
                    .height(20.dp)
            )
        } else {
            // 실제 데이터
            AsyncImage(
                model = image,
                contentDescription = "유적지 이미지",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.school_img),
                placeholder = painterResource(R.drawable.school_img)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Background_Normal.copy(alpha = 0.5f))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                nationAttributeName?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .background(Primary, shape = RoundedCornerShape(24.dp))
                            .padding(horizontal = 12.dp, vertical = 2.dp),
                        style = AppTextStyles.Label.Bold
                    )
                }
                lineAttributeName?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .background(Blue_Netural, shape = RoundedCornerShape(24.dp))
                            .padding(horizontal = 12.dp, vertical = 2.dp),
                        style = AppTextStyles.Label.Bold
                    )
                }
                regionAttributeName?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .background(Red_Netural, shape = RoundedCornerShape(24.dp))
                            .padding(horizontal = 12.dp, vertical = 2.dp),
                        style = AppTextStyles.Label.Bold
                    )
                }
            }

            name?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    text = it,
                    fontFamily = bitbit,
                    fontSize = 16.sp,
                    color = Label
                )
            }
        }
    }
}