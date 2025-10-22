@file:Suppress("DEPRECATION")
@file:OptIn(ExperimentalComposeUiApi::class)

package com.legacy.legacy_android.res.component.quiz

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.legacy.legacy_android.ui.theme.*
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.quiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.feature.screen.home.model.HintStatus
import com.legacy.legacy_android.feature.screen.home.model.QuizStatus
import com.legacy.legacy_android.res.component.adventure.RuinImage
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.service.RememberClickSound
import kotlinx.coroutines.delay
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.asAndroidBitmap

@Composable
fun QuizBox(
    data: List<GetQuizResponse>,
    quizStatus: QuizStatus,
    viewModel: HomeViewModel,
    ruin: RuinsIdResponse?
) {
    val (soundPool, soundId) = RememberClickSound()
    val selectedOption = remember { mutableStateOf<String?>(null) }
    val showCollection = remember { mutableStateOf(false) }

    when (quizStatus) {
        QuizStatus.WORKING -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(vertical = 27.dp, horizontal = 24.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (viewModel.uiState.quizNum.value == index) Primary else Label,
                                            shape = RoundedCornerShape(100.dp),
                                        )
                                        .size(8.dp)
                                )
                            }
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Q${viewModel.uiState.quizNum.value + 1}",
                            style = AppTextStyles.Title2.bold
                        )
                        Text(
                            text = data[viewModel.uiState.quizNum.value].ruinsName,
                            color = Label_Alternative,
                            style = AppTextStyles.Body1.medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Text(
                        text = data[viewModel.uiState.quizNum.value].quizProblem,
                        style = AppTextStyles.Title3.bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            data[viewModel.uiState.quizNum.value].optionValue.forEach { option ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            border = BorderStroke(
                                                4.dp,
                                                if (selectedOption.value == option) Primary else Fill_Normal
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(4.dp)
                                        .clickable {
                                            if (selectedOption.value != option) {
                                                selectedOption.value = option
                                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                            }
                                        }
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Fill_Normal)
                                            .border(
                                                1.dp,
                                                Line_Alternative,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .padding(vertical = 12.dp)
                                    ) {
                                        Text(text = option, style = AppTextStyles.Body1.bold)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(modifier = Modifier.padding(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomButton(
                                        onClick = {
                                            viewModel.uiState.quizNum.value = 0
                                            selectedOption.value = null
                                            viewModel.clearQuizAnswers()
                                            viewModel.updateQuizStatus(QuizStatus.NONE)
                                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                        },
                                        text = "나가기",
                                        modifier = Modifier.weight(1f),
                                        borderColor = Line_Netural,
                                        textColor = Label_Alternative,
                                        backgroundColor = Fill_Normal,
                                        contentPadding = PaddingValues(
                                            vertical = 12.dp,
                                            horizontal = 10.dp
                                        ),
                                        fontSize = AppTextStyles.Caption1.Bold.fontSize
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    CustomButton(
                                        onClick = {
                                            if (viewModel.uiState.hintData[viewModel.uiState.quizNum.value] == null) viewModel.updateHintStatus(
                                                HintStatus.CREDIT
                                            )
                                        },
                                        text = if (viewModel.uiState.hintData[viewModel.uiState.quizNum.value] == null) "300크레딧으로 힌트 확인" else "힌트: ${viewModel.uiState.hintData[viewModel.uiState.quizNum.value]}",
                                        modifier = Modifier.weight(3f),
                                        borderColor = if (viewModel.uiState.hintData[viewModel.uiState.quizNum.value] == null) Blue_Netural else Line_Normal,
                                        textColor = if (viewModel.uiState.hintData[viewModel.uiState.quizNum.value] == null) Blue_Netural else Line_Normal,
                                        backgroundColor = Fill_Normal,
                                        contentPadding = PaddingValues(
                                            vertical = 12.dp,
                                            horizontal = 16.dp
                                        ),
                                        fontSize = AppTextStyles.Caption1.Bold.fontSize
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    CustomButton(
                                        onClick = {
                                            selectedOption.value?.let { selected ->
                                                viewModel.addQuizAnswer(
                                                    data[viewModel.uiState.quizNum.value].quizId,
                                                    selected
                                                )
                                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                                if (viewModel.uiState.quizNum.value == 2) {
                                                    viewModel.submitQuizAnswers()
                                                    selectedOption.value = null
                                                } else {
                                                    viewModel.uiState.quizNum.value++
                                                    selectedOption.value = null
                                                }
                                            }
                                        },
                                        text = "다음",
                                        modifier = Modifier.weight(1f),
                                        borderColor = Line_Netural,
                                        textColor = Label_Alternative,
                                        backgroundColor = Fill_Normal,
                                        contentPadding = PaddingValues(
                                            vertical = 12.dp,
                                            horizontal = 10.dp
                                        ),
                                        fontSize = AppTextStyles.Caption1.Bold.fontSize
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        QuizStatus.SUCCESS -> {
            if (!showCollection.value) {
                CorrectView()
                LaunchedEffect(Unit) {
                    delay(3000)
                    showCollection.value = true
                }
            } else {
                CollectionView(viewModel, ruin)
            }
        }

        QuizStatus.RETRY -> {
            WrongView(
                selectedOption = selectedOption,
                showCollection = showCollection,
                viewModel = viewModel,
                quizNum = viewModel.uiState.quizNum
            )
        }

        QuizStatus.LOADING -> {
            LoadingView()
        }

        QuizStatus.SHARE -> {
            ShareView(viewModel, ruin)
        }

        else -> {}
    }
}

@Composable
fun LoadingView(
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 27.dp, horizontal = 37.dp)
        ) {
            Text(
                text = "로딩 중입니다!",
                style = AppTextStyles.Title2.bold
            )
            Text(
                text = "잠시만 기다려주세요.",
                style = AppTextStyles.Headline.medium,
                color = Label_Alternative
            )
        }
    }
}


@Composable
fun CorrectView(
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 27.dp, horizontal = 37.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.clap),
                contentDescription = "clap",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "축하해요!",
                style = AppTextStyles.Title2.bold
            )
            Text(
                text = "퀴즈를 모두 맞추다니.. 대단해요!",
                style = AppTextStyles.Headline.medium,
                color = Label_Alternative
            )
        }
    }
}


@Composable
fun CollectionView(
    viewModel: HomeViewModel,
    data: RuinsIdResponse?,
) {
    LaunchedEffect(Unit) {
        delay(1000)
        viewModel.updateQuizStatus(QuizStatus.SHARE)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 27.dp, horizontal = 37.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(5.dp)
            ) {
                RuinImage(
                    image = data!!.ruinsImage,
                    name = data.name,
                    nationAttributeName = data.card!!.nationAttributeName,
                    regionAttributeName = data.card.regionAttributeName,
                    lineAttributeName = data.card.lineAttributeName,
                    height = 400,
                )
            }
            Text(
                text = "카드를 획득했어요!",
                style = AppTextStyles.Title2.bold
            )
        }
    }
}

@Composable
fun WrongView(
    selectedOption: MutableState<String?>,
    quizNum: MutableState<Int>,
    showCollection: MutableState<Boolean>,
    viewModel: HomeViewModel,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 27.dp, horizontal = 37.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.sad),
                contentDescription = "sad",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "전부 맞추지 못했어요...",
                style = AppTextStyles.Title2.bold,
                textAlign = TextAlign.Center,
            )
            val wrongText =
                viewModel.uiState.wrongAnswers.joinToString(", ") { "${it + 1}번" }
            Text(
                text = "$wrongText 문제의 답이 잘못되었어요.\n다시 도전하면 맞출 수 있을 거예요!",
                style = AppTextStyles.Headline.medium,
                color = Label_Alternative,
                textAlign = TextAlign.Center
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Blue_Netural, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = {
                            selectedOption.value = null
                            quizNum.value = 0
                            showCollection.value = false
                            viewModel.clearQuizAnswers()
                            viewModel.updateQuizStatus(QuizStatus.NONE)
                        }
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp),
                    text = "확인",
                    color = Blue_Netural,
                    style = AppTextStyles.Body1.bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)
@Composable
fun ShareView(
    viewModel: HomeViewModel,
    data: RuinsIdResponse?
) {
    val context = LocalContext.current
    val captureController = remember { CaptureController() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(vertical = 27.dp, horizontal = 37.dp).fillMaxHeight()
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                // 캡처할 카드 영역 (배경 이미지 포함)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.4f)
                        .capturable(captureController)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        // 배경 이미지
                        Image(
                            painter = painterResource(R.drawable.cardbg),
                            contentDescription = "card background",
                            modifier = Modifier.fillMaxSize()
                        )
                        // 카드 이미지
                        RuinImage(
                            image = data!!.ruinsImage,
                            name = data.name,
                            nationAttributeName = data.card!!.nationAttributeName,
                            regionAttributeName = data.card.regionAttributeName,
                            lineAttributeName = data.card.lineAttributeName,
                            height = 400,
                        )
                    }
                }

                Text(
                    text = "카드를 획득했어요!",
                    style = AppTextStyles.Title2.bold
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(horizontal = 37.dp)
    ) {
        CustomButton(
            onClick = {
                coroutineScope.launch {
                    val imageBitmap = captureController.captureAsync().await()
                    val androidBitmap = imageBitmap.asAndroidBitmap()
                    val imageUri = saveBitmapToCache(context, androidBitmap)

                    imageUri?.let { uri ->
                        shareToInstagramStory(context, uri)
                    }
                }
            },
            text = "스토리 업로드",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f),
            borderColor = Blue_Netural,
            textColor = Blue_Netural,
            backgroundColor = Fill_Normal,
            contentPadding = PaddingValues(
                vertical = 12.dp,
                horizontal = 10.dp
            ),
            fontSize = AppTextStyles.Caption1.Bold.fontSize
        )

        CustomButton(
            onClick = {
                viewModel.clearQuizAnswers()
                viewModel.updateQuizStatus(QuizStatus.NONE)
            },
            text = "나가기",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f),
            borderColor = Line_Netural,
            textColor = Label_Alternative,
            backgroundColor = Fill_Normal,
            contentPadding = PaddingValues(
                vertical = 12.dp,
                horizontal = 10.dp
            ),
            fontSize = AppTextStyles.Caption1.Bold.fontSize
        )
    }
}

private fun saveBitmapToCache(context: Context, bitmap: android.graphics.Bitmap): Uri? {
    return try {
        val cachePath = java.io.File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = java.io.File(cachePath, "shared_card_${System.currentTimeMillis()}.png")
        val stream = java.io.FileOutputStream(file)
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun shareToInstagramStory(context: Context, cardUri: Uri) {
    val bgBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cardbg)
    val bgUri = saveBitmapToCache(context, bgBitmap) ?: return

    val instaIntent = Intent("com.instagram.share.ADD_TO_STORY").apply {
        setType("image/*")
        putExtra("interactive_asset_uri", cardUri) // 카드 스티커
        putExtra("background_image", bgUri)        // 뒤 배경
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.grantUriPermission("com.instagram.android", cardUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.grantUriPermission("com.instagram.android", bgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

    if (instaIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(instaIntent)
    } else {
        redirectToPlayStoreForInstagram(context)
    }
}



private fun redirectToPlayStoreForInstagram(context: Context) {
    val appStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android"))
    appStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(appStoreIntent)
}