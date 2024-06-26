package com.skele.jetcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SelectorButton(
    text : String,
    isSelected : Boolean,
    onSelect : () -> Unit = {}
){
    Surface(
        modifier = Modifier
            .clickable { onSelect() }
    ) {
        Text(
            text,
            modifier = Modifier.background(color = if (isSelected) Color.Cyan else Color.Gray)
        )
    }
}