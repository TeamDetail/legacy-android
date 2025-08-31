package com.legacy.legacy_android.res.component.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Background_Netural

@Composable
fun CustomDropDown(
    elements: List<String>,
    selectedValue: String,
    onClick: ()->Unit,
){
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp).height(48.dp).background(color = Background_Netural, shape = RoundedCornerShape(12.dp))
    ){
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            elements.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = onClick
                )
            }
        }
    }
}