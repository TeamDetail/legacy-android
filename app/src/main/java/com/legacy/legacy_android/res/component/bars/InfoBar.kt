package com.legacy.legacy_android.res.component.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Anternative
import com.legacy.legacy_android.R
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Yellow
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard
import java.text.NumberFormat
import java.util.Locale

@Composable
fun InfoBar(
    name : String,
    level : Number,
    money : Number,
    isTabClicked : Boolean,
    setTabClicked : ()-> Boolean
){
    Box (
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(50.dp)
            .background(Background_Normal, shape = RoundedCornerShape(size = 20.dp))
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth()){
            // 서버 값 들어오면 여기다가 프로필 넣기
            Column {
                Text(
                    text = name,
                    color = Label,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                        )
                )
                Text(
                    text = "LV. ${level.toString()}",
                    color = Label_Anternative,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Row (
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp)
                    .background(Fill_Normal, shape = RoundedCornerShape(size = 12.dp)),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
                ){
                Image(
                    painter = painterResource(R.drawable.coin),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
                Text(
                    text = NumberFormat.getNumberInstance(Locale.US).format(money).toString(),
                    color = Yellow,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = bitbit,
                    )
                )
            }
            Image(
                painter = painterResource(
                    if (isTabClicked) R.drawable.vector else R.drawable.tab
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(12.dp)
                    .background(Fill_Normal, shape = RoundedCornerShape(size = 12.dp))
                    .clickable { setTabClicked() }
            )
        }
    }
}