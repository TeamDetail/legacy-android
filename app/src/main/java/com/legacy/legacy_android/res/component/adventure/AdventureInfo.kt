package com.legacy.legacy_android.res.component.adventure
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.legacy.legacy_android.R
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Blue_Natural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Anternative
import com.legacy.legacy_android.ui.theme.Purple_Natural
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun AdventureInfo(
    name : String?,
    loc : LatLng?,
    info : String?,
    tags : List<String>?
    ){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .background(Black, shape = RoundedCornerShape(size=12.dp))
            .height(300.dp)
            .padding(12.dp)
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.8f)
                ) {
                    Text(
                        text = "유적지 탐험",
                        color = White,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = pretendard,
                            fontSize = 18.sp
                        )
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "선택한 블록",
                            color = Label_Anternative,
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontFamily = pretendard,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = "${loc?.latitude?.toInt()}, ${loc?.longitude?.toInt()}",
                            color = White,
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontFamily = pretendard,
                                fontSize = 18.sp
                            )
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "유적지 정보",
                            color = Label_Anternative,
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontFamily = pretendard,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = "$info",
                            color = White,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = pretendard,
                                fontSize = 18.sp
                            )
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
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = painterResource(R.drawable.schoo_img),
                        contentDescription = null
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)

                    ) {
                        tags?.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .background(Purple_Natural, shape = RoundedCornerShape(24.dp))
                            ) {
                                Text(
                                    text = item,
                                    color = Label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        text = name ?: "",
                        fontFamily = bitbit,
                        fontSize = 17.sp,
                        color = Label
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Blue_Natural, shape = RoundedCornerShape(8.dp))
            ){
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp),
                    text = "퀴즈 풀고 탐험하기",
                    color = Blue_Natural,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}