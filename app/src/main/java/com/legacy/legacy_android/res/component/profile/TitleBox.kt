package com.legacy.legacy_android.res.component.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.network.user.Title
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.res.component.title.TitleBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Purple_Normal

@Composable
fun TitleBox(
    title: Title,
    modifier: Modifier
) {
    var isClicked by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth(0.45f)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { isClicked = !isClicked }
            .background(
                if (isClicked) Background_Netural else Background_Alternative,
                RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Fill_Normal, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            TitleBar(title = title.name, modifier = Modifier.height(20.dp), styleId =  title.styleId)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
//            Text(
//                text = title.grade,
//                style = AppTextStyles.Body2.bold,
//                color = Primary
//            )
            Text(
                text = title.content,
                style = AppTextStyles.Caption1.Medium,
                color = Label_Netural
            )
        }
        AnimatedVisibility(
            visible = isClicked,
            enter = fadeIn(animationSpec = tween(300)) +
                    expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) +
                    shrinkVertically(animationSpec = tween(300))
        ) {
            if (isClicked) {
                CustomButton(
                    text = "장착",
                    onClick = { isClicked = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    borderColor = Purple_Normal,
                    textColor = Purple_Normal,
                    textStyle = AppTextStyles.Caption1.Bold,
                )
            }
        }
    }
}