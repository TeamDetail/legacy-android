package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.ruins.id.RuinsCommentResponse
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White

@Composable
fun CommentBox(comment: RuinsCommentResponse) {
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ){
        // 프로필 사진, 이름, 날짜
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = comment.userImgUrl,
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.school_img),
                placeholder = painterResource(R.drawable.school_img)
            )
            Column {
                Text(text = comment.userName, style = AppTextStyles.Body1.bold)
                Text(
                    text = comment.createAt.substringBefore("T"),
                    style = AppTextStyles.Caption2.regular,
                    color = Label_Alternative
                )
            }
        }
        Row(){
            for (i in 1..10) {
                if (i % 2 != 0) {
                    Image(
                        painter = painterResource(R.drawable.starhalfleft),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (i <= comment.rating) Primary else White)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.starhalfright),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (i <= comment.rating) Primary else White)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
        Text(text = comment.comment, style = AppTextStyles.Caption2.regular, )
    }
}