package com.legacy.legacy_android.res.component.modal.mail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.mail.ItemData
import com.legacy.legacy_android.feature.network.mail.MailResponse
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.ui.theme.*

@Composable
fun MailModal(
    onMailClick: (Boolean) -> Unit
) {
    val viewModel: MailViewModel = hiltViewModel()
    val mails = viewModel.mails
    val isLoading = viewModel.isLoading
    val items = viewModel.items
    val currentItem = viewModel.currentItem

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
                .background(Background_Normal, RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            /** 헤더 */
            Header(onMailClick)

            /** 콘텐츠 */
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                when {
                    isLoading -> LoadingView()
                    mails.isEmpty() -> EmptyView()
                    items.isNotEmpty() -> RewardView(items)
                    currentItem != null -> MailDetailView(currentItem)
                    else -> MailListView(mails) { mail -> viewModel.currentItem = mail }
                }
            }

            Spacer(Modifier.height(24.dp))

            /** 하단 버튼 */
            BottomButton(
                hasCurrentItem = currentItem != null,
                hasMails = mails.isNotEmpty(),
                onMailClick = onMailClick,
                onReceiveAll = { viewModel.getItems() },
                onCloseDetail = { viewModel.currentItem = null },
                hasRewardItems = items.isNotEmpty(),
            )
        }
    }
}

/** ---------------- 개별 UI ---------------- */

@Composable
private fun Header(onMailClick: (Boolean) -> Unit) {
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
            Text("우편함", fontFamily = bitbit, color = White, fontSize = 24.sp)
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = White,
            modifier = Modifier.clickable { onMailClick(false) }
        )
    }
}

@Composable
private fun LoadingView() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("로딩 중입니다.", style = AppTextStyles.Label.regular)
    }
}

@Composable
private fun EmptyView() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("우편함이 비었습니다.", style = AppTextStyles.Label.regular)
    }
}

@Composable
private fun RewardView(items: List<ItemData>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp) // 최대 높이 제한
            .verticalScroll(rememberScrollState())
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("우편함 보상 획득!", style = AppTextStyles.Title3.bold)
            Text(
                "우편함의 모든 보상을 수령했어요.",
                style = AppTextStyles.Caption1.Medium,
                color = Label_Alternative
            )
        }

        ItemGrid(items)
    }
}

@Composable
private fun MailDetailView(mail: MailResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(mail.mailTitle, style = AppTextStyles.Heading1.bold)
        Text(mail.mailContent, style = AppTextStyles.Body1.medium)
        Spacer(Modifier.height(100.dp))
        Text("구성품", style = AppTextStyles.Body1.medium)

        ItemGrid(mail.itemData)
    }
}

@Composable
private fun MailListView(
    mails: List<MailResponse>,
    onMailClick: (MailResponse) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        mails.forEach { mail ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMailClick(mail) }
            ) {
                Text(mail.mailTitle, style = AppTextStyles.Body1.bold)
                Text(
                    mail.sendAt,
                    style = AppTextStyles.Caption2.regular,
                    color = Label_Alternative
                )
                Spacer(Modifier.height(4.dp))

                ItemGrid(mail.itemData)
            }
        }
    }
}

@Composable
private fun ItemGrid(items: List<ItemData>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items.chunked(4).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach { item ->
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(Fill_Normal, RoundedCornerShape(8.dp))
                            .border(1.dp, Line_Alternative, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item.itemName, style = AppTextStyles.Caption2.regular)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomButton(
    hasCurrentItem: Boolean,
    hasMails: Boolean,
    hasRewardItems: Boolean,
    onMailClick: (Boolean) -> Unit,
    onReceiveAll: () -> Unit,
    onCloseDetail: () -> Unit
) {
    val text = when {
        !hasMails -> "일괄 수령"
        hasRewardItems -> "돌아가기"
        hasCurrentItem -> "돌아가기"
        else -> "일괄 수령"
    }

    val textColor = if (hasCurrentItem || hasRewardItems) Yellow_Netural else Label_Netural
    val borderColor = if (hasCurrentItem || hasRewardItems) Yellow_Netural else Line_Alternative

    CustomButton(
        onClick = {
            when {
                !hasMails -> onMailClick(false)
                hasRewardItems -> onMailClick(false)
                hasCurrentItem -> onCloseDetail()
                else -> onReceiveAll()
            }
        },
        text = text,
        modifier = Modifier.fillMaxWidth(),
        borderColor = borderColor,
        textColor = textColor,
        backgroundColor = Fill_Normal,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 10.dp),
        fontSize = AppTextStyles.Caption1.Bold.fontSize
    )
}
