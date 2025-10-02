package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    image: String,
    name: String,
    nationAttributeName: String,
    regionAttributeName: String,
    lineAttributeName: String,
    height: Int,
){
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
            Text(
                text = nationAttributeName,
                modifier = Modifier
                    .background(Primary, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                style = AppTextStyles.Label.Bold
            )
            Text(
                text = lineAttributeName,
                modifier = Modifier
                    .background(Blue_Netural, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                style = AppTextStyles.Label.Bold
            )
            Text(
                text = regionAttributeName,
                modifier = Modifier
                    .background(Red_Netural, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                style = AppTextStyles.Label.Bold
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            text = name,
            fontFamily = bitbit,
            fontSize = 16.sp,
            color = Label
        )
    }
}