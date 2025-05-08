package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.pretendard

@Composable
fun PackWrap(
    title: String,
    newList : List<PackModel>){
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = title,
            color = Label,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            fontFamily = pretendard,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 800.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            userScrollEnabled = false
        ) {
            items(newList) { pack ->
                Pack(packModel = pack)
            }
        }
    }
}