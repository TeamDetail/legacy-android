package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Green_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun RuinsBox(
    data: RuinsIdResponse?,
    viewModel: CourseViewModel
){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(200.dp).clickable {if(viewModel.uiState.createSelectedRuins!!.size < 30){viewModel.setCreateSelectedRuins(data)}}
    ){
        // 상세 정보 
        Column (
            modifier = Modifier.fillMaxWidth(0.6f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ){
            // 있는지 없는지
            if (viewModel.uiState.createSelectedRuins!!.contains(data)) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(Green_Netural, shape = RoundedCornerShape(12.dp))
                ) {
                    val index = viewModel.uiState.createSelectedRuins?.indexOf(data) ?: -1
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
                Text(text = "#${data?.ruinsId}", color = Label_Alternative, style = AppTextStyles.Caption1.Medium)
                Text(text = data?.name ?: "이름이 없습니다.", style = AppTextStyles.Headline.bold, color = Label)
                Spacer(Modifier.height(2.dp))
                //TODO 별점 만들기
            }
            // divider
            Box(Modifier.height(1.dp).fillMaxWidth().background(Line_Alternative))
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
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .clip(RoundedCornerShape(12.dp))
                .padding(5.dp)
        ) {
            if (data?.ruinsImage.isNullOrBlank()) {
                SkeletonBox(
                    modifier = Modifier
                        .matchParentSize()
                )
            } else {
                AsyncImage(
                    model = data?.ruinsImage,
                    contentDescription = "유적지 이미지",
                    modifier = Modifier
                        .matchParentSize()
                        .border(1.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
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
                    text = data?.name ?: "",
                    fontFamily = bitbit,
                    fontSize = 16.sp,
                    color = Label
                )
            }
        }
    }
}
