package com.legacy.legacy_android.feature.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.button.BackButton
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Primary

@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val profile by viewModel.profileFlow.collectAsState()
    var tempImg by remember { mutableStateOf<String?>(null) }
    var tempDescription by remember { mutableStateOf<String?> (null) }

    LaunchedEffect(profile) {
        tempImg = profile?.imageUrl
        /**추가하기*/
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
            .padding(vertical = 40.dp, horizontal = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .align(Alignment.TopStart)
                .verticalScroll(rememberScrollState())
        ) {
            BackButton(
                selectedId = 5,
                title = "프로필 수정",
                navHostController = navHostController
            )
            Spacer(modifier = modifier.height(24.dp))
            // 여기서 프로필 윗부분
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "프로필 이미지",
                        style = AppTextStyles.Headline.bold,
                        color = Label_Netural
                    )
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AsyncImage(
                            model = tempImg,
                            contentDescription = "프로필 이미지",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.school_img),
                            error = painterResource(R.drawable.school_img)
                        )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                                        .border(1.dp, Primary, RoundedCornerShape(8.dp))
                                        .clickable { /* 이미지 변경 로직 */ }
                                        .padding(vertical = 12.dp)
                                ) {
                                    Text(
                                        text = "이미지 변경",
                                        color = Primary,
                                        style = AppTextStyles.Caption1.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "자기소개",
                        style = AppTextStyles.Headline.bold,
                        color = Label_Netural
                    )
                    TextField(
                        value = tempDescription ?: "",
                        onValueChange = { tempDescription = it },
                        modifier = Modifier.fillMaxWidth()
                            .background(color = Background_Normal, shape = RoundedCornerShape(12.dp))
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        placeholder = { Text(text = "자기소개를 적어주세요!") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Label, unfocusedTextColor = Label,
                            focusedContainerColor = Fill_Normal,
                            unfocusedContainerColor = Fill_Normal,
                            disabledContainerColor = Fill_Normal,
                            focusedIndicatorColor = Fill_Normal,
                            unfocusedIndicatorColor = Fill_Normal,
                            disabledIndicatorColor = Fill_Normal,
                            unfocusedPlaceholderColor = Label,
                            focusedPlaceholderColor = Label,
                        )
                    )
                }
            }
        }
    }