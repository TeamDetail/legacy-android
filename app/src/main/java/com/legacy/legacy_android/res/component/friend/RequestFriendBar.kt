package com.legacy.legacy_android.res.component.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.friend.FriendReqResponse
import com.legacy.legacy_android.feature.screen.friend.FriendViewModel
import com.legacy.legacy_android.res.component.title.TitleBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural

@Composable
fun RequestFriendBar(viewModel: FriendViewModel, data: FriendReqResponse, isReceive: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = if (isReceive) data.senderProfileImage else data.receiverProfileImage,
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.school_img),
                placeholder = painterResource(R.drawable.school_img)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .width(144.dp)
            ) {
                Text(
                    text = if (isReceive) data.senderNickname else data.receiverNickname,
                    style = AppTextStyles.Headline.bold,
                    color = Label
                )
                Text(
                    text = "Lv. ${if (isReceive) data.senderLevel else data.receiverLevel}",
                    style = AppTextStyles.Label.Medium,
                    color = Label_Alternative
                )
                TitleBar(title = "ㅇㅇ", modifier = Modifier.height(20.dp))
            }
        }
        Column (
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isReceive) Arrangement.SpaceBetween else Arrangement.Center
        ){
            if (isReceive) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = Fill_Netural,
                            shape = CircleShape
                        )
                        .clickable{
                            viewModel.acceptRequest(data.requestId)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "친구 추가",
                        tint = Label_Netural,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Fill_Netural,
                        shape = CircleShape
                    )
                    .clickable{
                        if (isReceive) viewModel.declineRequest(data.requestId) else viewModel.deleteSentRequest(data.requestId)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "친구 추가",
                    tint = Label_Netural,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}