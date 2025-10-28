package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.res.component.adventure.RuinImage
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Alternative
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White

@Composable
fun RuinsBox(
    data: RuinsIdResponse?,
    viewModel: CourseViewModel
){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.height(200.dp).clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource()})
        {
            if(viewModel.uiState.createSelectedRuins!!.size < 30){viewModel.setCreateSelectedRuins(data)}
        }
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
                        .background(Blue_Netural, shape = RoundedCornerShape(12.dp))
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
            ) {
                Text(
                    text = "#${data?.ruinsId}",
                    color = Label_Alternative,
                    style = AppTextStyles.Caption1.Medium
                )
                Text(
                    text = data?.name ?: "이름이 없습니다.",
                    style = AppTextStyles.Headline.bold,
                    color = Label
                )
                Spacer(Modifier.height(2.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    for (i in 1..10) {
                        if (i % 2 != 0) {
                            Image(
                                painter = painterResource(R.drawable.starhalfleft),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(if (i <= data!!.averageRating) Primary else White)
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.starhalfright),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(if (i <= data!!.averageRating) Primary else White)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "(${data!!.countComments})", style = AppTextStyles.Caption2.regular, color = Fill_Alternative)
                }
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
                RuinImage(
                    image = data.ruinsImage,
                    name = data.name,
                    nationAttributeName = data.card!!.nationAttributeName,
                    regionAttributeName = data.card.regionAttributeName,
                    lineAttributeName = data.card.lineAttributeName,
                    height = 180
                )
            }
        }
    }
}
