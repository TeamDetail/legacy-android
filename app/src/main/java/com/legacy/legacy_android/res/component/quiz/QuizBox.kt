package com.legacy.legacy_android.res.component.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun QuizBox(
    data: List<GetQuizResponse>,
    quizStatus: QuizStatus,
    viewModel: HomeViewModel,
    ruin: RuinsIdResponse?
) {

    val (soundPool, soundId) = RememberClickSound()
    val selectedOption = remember { mutableStateOf<String?>(null) }
    val quizNum = remember { mutableIntStateOf(0) }
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
                                            color = if (quizNum.intValue == index) Primary else Label,
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
                        Text(text = "Q${quizNum.intValue + 1}", style = AppTextStyles.Title2.bold)
                        Text(
                            text = data[quizNum.intValue].ruinsName,
                            color = Label_Alternative,
                            style = AppTextStyles.Body1.medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Text(
                        text = data[quizNum.intValue].quizProblem,
                        style = AppTextStyles.Title3.bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            data[quizNum.intValue].optionValue.forEach { option ->
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
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    CustomButton(
                                        onClick = {
                                            quizNum.intValue = 0
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
                                        onClick = { viewModel.updateHintStatus(HintStatus.CREDIT) },
                                        text = "300크레딧으로 힌트 확인",
                                        modifier = Modifier.weight(3f),
                                        borderColor = Blue_Netural,
                                        textColor = Blue_Netural,
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
                                                    data[quizNum.intValue].quizId,
                                                    selected
                                                )
                                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                                if (quizNum.intValue == 2) {
                                                    viewModel.submitQuizAnswers()
                                                    selectedOption.value = null
                                                } else {
                                                    quizNum.intValue++
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
                quizNum = quizNum
            )
        }

        QuizStatus.LOADING -> {
            LoadingView()
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
        delay(5000)
        viewModel.clearQuizAnswers()
        viewModel.updateQuizStatus(QuizStatus.NONE)
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
