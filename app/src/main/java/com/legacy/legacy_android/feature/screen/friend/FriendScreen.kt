package com.legacy.legacy_android.feature.screen.friend

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.network.Nav
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.pretendard
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.res.component.friend.FriendBar

@Composable
fun FriendScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    val selectedId = Nav.getNavStatus()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            Nav.setNavStatus(selectedId)
                            when (selectedId) {
                                0 -> navHostController.navigate("MARKET")
                                1 -> navHostController.navigate("ACHIEVE")
                                2 -> navHostController.navigate("HOME")
                                3 -> navHostController.navigate("TRIAL")
                                4 -> navHostController.navigate("RANKING")
                            }
                        }
                )
                Text(
                    text = "친구",
                    color = Label,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard,
                )
            }
            // 여기서 프로필 윗부분
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                ){
                Spacer(modifier = Modifier.height(16.dp))
                FriendBar(name = "박재민", title = "자본주의", level = 99, profile = R.drawable.temp_profile)
            }
            }
    }
    }
