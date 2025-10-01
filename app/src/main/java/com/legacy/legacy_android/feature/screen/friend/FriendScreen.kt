package com.legacy.legacy_android.feature.screen.friend

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.res.component.button.BackButton
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.friend.FriendBar
import com.legacy.legacy_android.res.component.friend.RequestFriendBar
import com.legacy.legacy_android.res.component.modal.AlertModal
import com.legacy.legacy_android.res.component.modal.FriendModal
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary

@Composable
fun FriendScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val friendList = listOf("목록", "대기 중", "추가")
    val selectedId = Nav.getNavStatus()
    LaunchedEffect(Unit) {
        viewModel.changeFriendCode("")
        viewModel.fetchMyCode()
        viewModel.fetchFriendList()
        viewModel.fetchSentRequestList()
        viewModel.fetchRequestList()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
    ) {
        if (viewModel.uiState.setDeleteFriend) {
            FriendModal(
                friendName = viewModel.uiState.currentFriend?.nickname ?: "",
                onConfirm = {
                    viewModel.deleteFriend(viewModel.uiState.currentFriend!!.userId)
                    viewModel.setDeleteFriend(false)
                },
                onCancel = {
                    viewModel.setDeleteFriend(false)
                }
            )
        }
        if (viewModel.uiState.requestError != null) {
            AlertModal(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding()
                    .padding(bottom = 120.dp),
                isCorrect = viewModel.uiState.requestError == false,
                incorrectMessage = "요청 실패 : 이미 보냈거나 \n존재하지 않는 친구 코드입니다.",
                correctMessage = "친구 요청 성공!"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                BackButton(
                    title = "친구",
                    navHostController = navHostController,
                    selectedId = selectedId
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    friendList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.uiState.friendStatus,
                            onClick = { viewModel.changeFriendStatus(index) },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Netural
                        )
                    }
                }
                when (viewModel.uiState.friendStatus) {
                    0 -> ListScreen(viewModel = viewModel)
                    1 -> WaitingScreen(viewModel = viewModel)
                    2 -> AddFriendScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ListScreen(viewModel: FriendViewModel) {
    if (viewModel.uiState.friendList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "친구가 없습니다!",
                    style = AppTextStyles.Heading1.bold,
                    color = Label
                )
                CustomButton(
                    text = "추가하러 가기",
                    onClick = { viewModel.changeFriendStatus(2) },
                    textColor = Label_Netural,
                    borderColor = Line_Alternative,
                    modifier = Modifier.width(160.dp)
                )
            }
        }
    } else {
        Spacer(modifier = Modifier.height(4.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            viewModel.uiState.friendList.forEach { friend ->
                FriendBar(
                    data = friend,
                    viewModel = viewModel
                )
            }
        }
    }
}
@Composable
fun WaitingScreen(viewModel: FriendViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchSentRequestList()
        viewModel.fetchRequestList()
    }
    var receiveExpanded by remember { mutableStateOf(false) }
    var presentExpanded by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(4.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Background_Netural, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp)
                .clickable { presentExpanded = !presentExpanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append(
                        text = "보낸 친구 추가 "
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Label,
                            fontWeight = AppTextStyles.Body1.bold.fontWeight
                        )
                    ) {
                        append(viewModel.uiState.sentRequestList.size.toString())
                    }
                },
                style = AppTextStyles.Body1.regular,
                color = Label_Alternative
            )
            Icon(
                imageVector = if (!presentExpanded) {
                    Icons.Default.KeyboardArrowDown
                } else {
                    Icons.Default.KeyboardArrowUp
                },
                tint = Label,
                modifier = Modifier.size(32.dp),
                contentDescription = null
            )
        }
        AnimatedVisibility(visible = presentExpanded) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                viewModel.uiState.sentRequestList.forEach { it ->
                    RequestFriendBar(
                        data = it,
                        isReceive = false,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Background_Netural, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp)
                .clickable { receiveExpanded = !receiveExpanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append(
                        text = "받은 친구 추가 "
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Label,
                            fontWeight = AppTextStyles.Body1.bold.fontWeight
                        )
                    ) {
                        append(viewModel.uiState.receiveRequestList.size.toString())
                    }
                },
                style = AppTextStyles.Body1.regular,
                color = Label_Alternative
            )
            Icon(
                imageVector = if (!receiveExpanded) {
                    Icons.Default.KeyboardArrowDown
                } else {
                    Icons.Default.KeyboardArrowUp
                },
                tint = Label,
                modifier = Modifier.size(32.dp),
                contentDescription = null
            )
        }
        AnimatedVisibility(visible = receiveExpanded) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                viewModel.uiState.receiveRequestList.forEach { it ->
                    RequestFriendBar(
                        data = it,
                        isReceive = true,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun AddFriendScreen(viewModel: FriendViewModel) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "내 친구 코드", color = Label_Netural, style = AppTextStyles.Body1.medium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Background_Netural, shape = RoundedCornerShape(8.dp))
                    .height(60.dp)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewModel.uiState.myCode,
                    color = Label,
                    style = AppTextStyles.Heading1.bold
                )
                Box(
                    modifier = Modifier.background(
                        color = Primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                ) {
                    Text(
                        text = "복사",
                        style = AppTextStyles.Body1.medium,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .clickable { copyToClipboard(context, viewModel.uiState.myCode) })
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "친구 코드로 추가하기", color = Label_Netural, style = AppTextStyles.Body1.medium)
            TextField(
                value = viewModel.uiState.friendCode,
                onValueChange = { code -> viewModel.changeFriendCode(code) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "추가하고 싶은 친구의 코드 입력...", color = Label_Alternative) },
                trailingIcon = {
                    val isEmpty = viewModel.uiState.friendCode.isBlank()
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = if (!isEmpty) Primary else Fill_Netural,
                                shape = CircleShape
                            )
                            .clickable(enabled = !isEmpty) {
                                viewModel.sendFriendRequest()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "친구 추가",
                            tint = if (!isEmpty) Label_Netural else Label_Netural,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Label,
                    unfocusedTextColor = Label,
                    focusedContainerColor = Background_Normal,
                    unfocusedContainerColor = Background_Normal,
                    disabledContainerColor = Background_Normal,
                    focusedIndicatorColor = Background_Normal,
                    unfocusedIndicatorColor = Background_Normal,
                    disabledIndicatorColor = Background_Normal
                )
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Line_Alternative))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "이름으로 검색하기", color = Label_Netural, style = AppTextStyles.Body1.medium)
//            CustomSearchBar(
//                query = viewModel.uiState.searchFriend,
//                placeholder = "친구 이름으로 검색..",
//                onSearch = ",
//                modifier = Modifier
//            )
        }
    }
}

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
}