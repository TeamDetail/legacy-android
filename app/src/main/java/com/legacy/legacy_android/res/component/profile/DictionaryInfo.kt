package com.legacy.legacy_android.res.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun DictionaryInfo(
    modifier: Modifier,
//    tags: List<String>?,
    title: String?,
    img: String,
){
    Box(
        modifier = modifier
            .width(122.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .padding(5.dp)
    ) {
        if (img.isNullOrBlank()) {
            SkeletonBox(
                modifier = modifier
                    .matchParentSize()
            )
        } else {
            AsyncImage(
                model = img,
                contentDescription = "유적지 이미지",
                modifier = modifier
                    .matchParentSize()
                    .border(1.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.school_img),
                placeholder = painterResource(R.drawable.school_img)
            )
            Box(
                modifier = modifier
                    .matchParentSize()
                    .background(Background_Normal.copy(alpha = 0.5f))
                    .clip(RoundedCornerShape(12.dp))
            )
            Text(
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                text = title ?: "",
                fontFamily = bitbit,
                fontSize = 16.sp,
                color = Label
            )
        }
    }
}