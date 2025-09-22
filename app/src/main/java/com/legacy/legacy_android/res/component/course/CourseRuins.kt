package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.ruins.id.CourseRuinsResponse
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Alternative
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Green_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun CourseRuins(
    data: CourseRuinsResponse,
    index: Int
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(200.dp).fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.15f)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (data.clear) Green_Netural else Fill_Normal,
                        shape = RoundedCornerShape(100.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (index+1).toString(),
                    style = AppTextStyles.Heading2.bold,
                    color = Label
                )
            }
        }
        // 상세 정보
        Column (
            modifier = Modifier.fillMaxWidth(0.5f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ){
            // 있는지 없는지
            if (data.clear) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(Green_Netural, shape = RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = if (index >= 0) (index + 1).toString() else "",
                        style = AppTextStyles.Body2.bold,
                        color = Label,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            // 이름, 번호, 별점
            Column (
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ){
                Text(text = "#${data.data.ruinsId}", color = Label_Alternative, style = AppTextStyles.Caption1.Medium)
                Text(text = data.data.name, style = AppTextStyles.Headline.bold, color = Label)
                Spacer(Modifier.height(2.dp))
                val rating = data.data.averageRating
                val commentCount = data.data.countComments.toString()
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    for (i in 1..10) {
                        if (i % 2 != 0) {
                            Image(
                                painter = painterResource(R.drawable.starhalfleft),
                                contentDescription = null,
                                modifier = Modifier.height(12.dp),
                                colorFilter = ColorFilter.tint(
                                    if (i <= rating) Primary else White
                                )
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.starhalfright),
                                contentDescription = null,
                                modifier = Modifier.height(12.dp),
                                colorFilter = ColorFilter.tint(
                                    if (i <= rating) Primary else White
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    Text(
                        text = "(${commentCount})",
                        style = AppTextStyles.Body2.medium,
                        color = Fill_Alternative
                    )
                }
            }
            // divider
            Box(Modifier.height(1.dp).fillMaxWidth(0.9f).background(Line_Alternative))
            Row {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Label_Alternative, fontSize = AppTextStyles.Caption2.regular.fontSize, fontWeight = AppTextStyles.Caption2.regular.fontWeight)) {
                            append("탐험자 수\n")
                        }
                        append("20000명")
                    },
                    style = AppTextStyles.Body2.bold, color = Label
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .clip(RoundedCornerShape(12.dp))
                .padding(5.dp)
        ) {
            if (data.data.ruinsImage.isBlank()) {
                SkeletonBox(
                    modifier = Modifier
                        .matchParentSize()
                )
            } else {
                AsyncImage(
                    model = data.data.ruinsImage,
                    contentDescription = "유적지 이미지",
                    modifier = Modifier
                        .matchParentSize()
                        .border(2.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.school_img),
                    placeholder = painterResource(R.drawable.school_img)
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Background_Normal.copy(alpha = 0.5f))
                        .clip(RoundedCornerShape(12.dp))
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    text = data.data.name,
                    fontFamily = bitbit,
                    fontSize = 16.sp,
                    color = Label
                )
            }
        }
    }
}
