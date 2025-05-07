package com.legacy.legacy_android.feature.screen.market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.bitbit

@Composable
fun MarketScreen(modifier: Modifier = Modifier,
                viewModel: MarketViewModel = hiltViewModel(),
                 navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Background_Normal)
    ){
        Row {
            Image(
                painter = painterResource(R.drawable.shop),
                contentDescription = null
            )
            Text(
                text = "상점",
                color = Label,
                fontSize = 28.sp,
                fontFamily = bitbit,
            )
        }
    }
}