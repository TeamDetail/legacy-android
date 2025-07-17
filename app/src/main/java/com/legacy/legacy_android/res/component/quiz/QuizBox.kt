package com.legacy.legacy_android.res.component.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.legacy.legacy_android.ui.theme.Background_Normal
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizResponse
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.service.RememberClickSound
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.bitbit
@Composable
fun QuizBox(
    data: List<GetQuizResponse>,
    quizStatus: MutableState<Int>,
    onConfirm: () -> Unit,
    viewModel: HomeViewModel,
    name: String?,
    ruinsId: Int?,
    image: String?
) {
    val (soundPool, soundId) = RememberClickSound()
    val selectedOption = remember { mutableStateOf<String?>(null) }
    if (quizStatus.value == 0 || quizStatus.value == 2 || quizStatus.value == 1) {
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
                    .padding(vertical = 27.dp, horizontal = 37.dp)
                    .fillMaxSize()
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                    ) {
                        repeat(3) { index ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (quizStatus.value == index) Primary else Label,
                                        shape = RoundedCornerShape(100.dp),
                                    )
                                    .size(8.dp)
                            )
                        }
                    }
                }
                // Question 번호와 name
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Q${quizStatus.value + 1}",
                        style = AppTextStyles.Title2.bold
                    )

                    // 유적지 이름
                    Text(
                        text = data[quizStatus.value].ruinsName,
                        color = Label_Alternative,
                        style = AppTextStyles.Body1.medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    text = data[quizStatus.value].quizProblem,
                    style = AppTextStyles.Title3.bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                // 저거 그 뭐더라 이름이랑 카드 나오는 거
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 문제 이름
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        data[quizStatus.value].optionValue.forEach { option ->
                            Box(
                                modifier = Modifier
                                    .border(
                                        border = BorderStroke(
                                            width = 4.dp,
                                            color = if (selectedOption.value == option) Primary else Fill_Normal
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
                                            width = 1.dp,
                                            color = Line_Alternative,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(vertical = 12.dp)
                                ) {
                                    Text(
                                        text = option,
                                        style = AppTextStyles.Body1.bold
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    onConfirm()
                                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                        .border(
                                            border = BorderStroke(1.dp, Line_Netural),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(vertical = 12.dp, horizontal = 10.dp)
                                        .clickable {
                                            quizStatus.value = 0
                                            selectedOption.value = null
                                            viewModel.answerOption.clear()
                                            viewModel.quizIndex.clear()
                                            viewModel.quizIdData.value = null
                                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                        }
                                ) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "나가기",
                                        color = Label_Alternative,
                                        style = AppTextStyles.Caption1.Bold
                                    )
                                }
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                        .border(
                                            border = BorderStroke(1.dp, Blue_Netural),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(vertical = 12.dp, horizontal = 30.dp)
                                ) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "힌트 확인하기",
                                        color = Blue_Netural,
                                        style = AppTextStyles.Caption1.Bold
                                    )
                                }
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                        .border(
                                            border = BorderStroke(1.dp, Line_Netural),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(vertical = 12.dp, horizontal = 10.dp)
                                        .clickable {
                                            selectedOption.value?.let { selected ->
                                                viewModel.answerOption.add(selected)
                                                viewModel.quizIndex.add(data[quizStatus.value].quizId)
                                                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                                if (quizStatus.value == 2) {
                                                    viewModel.fetchQuizAnswer()
                                                    selectedOption.value = null
                                                } else if (quizStatus.value == 1 || quizStatus.value == 0) {
                                                    quizStatus.value++
                                                    selectedOption.value = null
                                                }
                                            }
                                        }
                                ) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "다음",
                                        color = Label_Alternative,
                                        style = AppTextStyles.Caption1.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }else if(quizStatus.value == 3){
        CorrectView(quizStatus, selectedOption, viewModel)
    }else if (quizStatus.value == 4) {
        CollectionView(quizStatus, selectedOption, viewModel, name, ruinsId, image)
    }else if(quizStatus.value == 5){
        WrongView(quizStatus, selectedOption, viewModel)
    }

}

@Composable
fun CorrectView(
    quizStatus: MutableState<Int>,
    selectedOption: MutableState<String?>,
    viewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        quizStatus.value = 4
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
    quizStatus: MutableState<Int>,
    selectedOption: MutableState<String?>,
    viewModel: HomeViewModel,
    name: String?,
    ruinsId: Int?,
    image: String?
){

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(5000)
        quizStatus.value = 0
        selectedOption.value = null
        viewModel.answerOption.clear()
        viewModel.quizIndex.clear()
        viewModel.quizIdData.value = null
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
        )   {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(5.dp)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "유적지 이미지",
                    modifier = Modifier
                        .border(1.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .matchParentSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.school_img),
                    placeholder = painterResource(R.drawable.school_img)
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Background_Normal.copy(alpha = 0.5f))
                        .clip(RoundedCornerShape(12.dp))
                )

//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(2.dp),
//                        modifier = Modifier
//                            .align(Alignment.TopStart)
//
//                    ) {
//
//                        // 태그 매핑
//                        tags?.forEach { item ->
//                            Box(
//                                modifier = Modifier
//                                    .background(Purple_Netural, shape = RoundedCornerShape(24.dp))
//                            ) {
//                                Text(
//                                    text = item,
//                                    style = AppTextStyles.Label.Bold,
//                                    modifier = Modifier
//                                        .padding(horizontal = 12.dp, vertical = 4.dp)
//                                )
//                            }
//                        }
//                    }

                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    text = if (name != null) name else "",
                    fontFamily = bitbit,
                    fontSize = 16.sp,
                    color = Label
                )
            }
        }
            Text(
                text = "카드를 획득했어요!",
                style = AppTextStyles.Title2.bold
            )
        }
    }

@Composable
fun WrongView(
    quizStatus: MutableState<Int>,
    selectedOption: MutableState<String?>,
    viewModel: HomeViewModel,
){
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
        )   {
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
            val wrongText = viewModel.wrongAnswers.joinToString(", ") { "${it + 1}번" }
            Text(
                text = "${wrongText} 문제의 답이 잘못되었어요.\n다시 도전하면 맞출 수 있을 거예요!",
                style = AppTextStyles.Headline.medium,
                color = Label_Alternative
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Blue_Netural, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = {
                            quizStatus.value = 0
                            selectedOption.value = null
                            viewModel.answerOption.clear()
                            viewModel.quizIndex.clear()
                            viewModel.quizIdData.value = null
                            viewModel.wrongAnswers.clear()
                        }
                    )
            ){
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