package com.legacy.legacy_android.feature.screen.market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.screen.login.LoginViewModel
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.marketItem.MarketInfo
import com.legacy.legacy_android.res.component.marketItem.Pack
import com.legacy.legacy_android.res.component.marketItem.PackWrap
import com.legacy.legacy_android.res.component.marketItem.Packs
import com.legacy.legacy_android.res.component.marketItem.StatusButton
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.bitbit
import com.legacy.legacy_android.ui.theme.pretendard

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
        Column (
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.shop),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "상점",
                        color = Label,
                        fontSize = 28.sp,
                        fontFamily = bitbit,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusButton(
                        value = viewModel.marketStatus,
                        setValue = { viewModel.setMarketStatus(true) },
                        text = "카드 팩"
                    )
                    StatusButton(
                        value = !viewModel.marketStatus,
                        setValue = { viewModel.setMarketStatus(false) },
                        text = "크레딧 충전"
                    )
                }
                MarketInfo(quantity = 3, magnification = 1.75)
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // 팩 파는 구간
                    PackWrap(title = "앤더슨팩", newList = Packs.bluePackList)
                    PackWrap(title = "코팩", newList = Packs.purplePackList)
                    PackWrap(title = "여드름팩", newList = Packs.redPackList)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )
        }
        // NavBar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .zIndex(7f)
        ) {
            NavBar(navHostController = navHostController)
        }
    }
}