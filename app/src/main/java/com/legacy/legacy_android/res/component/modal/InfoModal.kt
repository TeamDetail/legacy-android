package com.legacy.legacy_android.res.component.modal

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.ui.theme.*
import androidx.core.net.toUri

@Composable
fun InfoModal(
    onInfoClick: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black.copy(alpha = 0.75f))
            .zIndex(1500f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(Background_Normal, RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                /** 헤더 */
                Header(onInfoClick)

                /** 내용 */
                Column (
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ){
                    CustomButton(
                        text = "피드백 및 이벤트",
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "https://docs.google.com/forms/d/e/1FAIpQLSe6HJINLg-oTf5oGXpPU5rL4EMvIkJu6R1WXAgm7RJclMd2Nw/viewform?pli=1".toUri()
                            )
                            context.startActivity(intent)
                            onInfoClick(true)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = Purple_Normal,
                        textColor = Purple_Normal,
                        textStyle = AppTextStyles.Caption1.Bold
                    )
                    CustomButton(
                        text = "인스타그램",
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "https://www.instagram.com/legacyofdetail/".toUri()
                            )
                            context.startActivity(intent)
                            onInfoClick(true)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = Red_Netural,
                        textColor = Red_Netural,
                        textStyle = AppTextStyles.Caption1.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(onInfoClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("정보", style = AppTextStyles.Heading2.bold)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = White,
            modifier = Modifier.clickable { onInfoClick(false) }
        )
    }
}
