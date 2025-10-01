package com.legacy.legacy_android.res.component.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun CustomSearchBar(
    query: MutableState<String>,
    placeholder: String,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = { Text(placeholder, color = Label_Alternative)  },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .background(Fill_Normal, RoundedCornerShape(12.dp)).fillMaxWidth(),
        textStyle = AppTextStyles.Label.Medium,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(query.value)
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Fill_Normal,
            unfocusedContainerColor = Fill_Normal,
            disabledContainerColor = Fill_Normal,
            focusedIndicatorColor = Fill_Normal,
            unfocusedIndicatorColor = Fill_Normal,
            disabledIndicatorColor = Fill_Normal,
            focusedPlaceholderColor = Label,
            unfocusedPlaceholderColor = Label,
            focusedLeadingIconColor = Label,
            unfocusedLeadingIconColor = Label,
        )
    )
}
