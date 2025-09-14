package com.legacy.legacy_android.res.component.modal.mail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.legacy.legacy_android.R
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun MailModal(
    onMailClick: (Boolean) -> Unit
) {
    val viewModel: MailViewModel = hiltViewModel()
    val mails = viewModel.mails
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.loadMails()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A2B2C).copy(alpha = 0.75f))
            .zIndex(5000f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {}
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.mail),
                        contentDescription = "mail",
                        modifier = Modifier.size(36.dp)
                    )
                    Text(text = "우편함", fontFamily = bitbit, color = White, fontSize = 24.sp)
                }
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = White,
                    modifier = Modifier.clickable { onMailClick(false) }
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "로딩 중입니다.", style = AppTextStyles.Label.regular)
                    }
                } else if (mails.isEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "우편함이 비었습니다.", style = AppTextStyles.Label.regular)
                    }
                } else {
                    mails.forEach { mail ->
                        Text(
                            text = mail.mailTitle,
                            style = AppTextStyles.Body1.bold
                        )
                        Text(
                            text = mail.sendAt,
                            style = AppTextStyles.Caption2.regular,
                            color = Label_Alternative
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ){
                            mail.itemData.forEach { item ->
                                Box(
                                    Modifier.size(40.dp)
                                        .background(Fill_Normal, RoundedCornerShape(8.dp))
                                        .border(
                                            width = 1.dp,
                                            color = Line_Alternative,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                ) {
                                    Text(item.itemName, style = AppTextStyles.Caption2.regular)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Fill_Normal, RoundedCornerShape(12.dp))
                    .border(1.dp, Yellow_Netural, RoundedCornerShape(12.dp))
                    .padding(vertical = 12.dp, horizontal = 10.dp)
                    .clickable {
                        if (!mails.isEmpty()){viewModel.getItems()}
                    }
            ) {
                Text("일괄 수령", color = Yellow_Netural, style = AppTextStyles.Caption1.Bold)
            }
        }
    }
}