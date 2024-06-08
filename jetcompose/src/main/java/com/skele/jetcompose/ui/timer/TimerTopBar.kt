package com.skele.jetcompose.ui.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimerTopBar(
    onSettingClick : () -> Unit = {}
){
    Surface(
        modifier = Modifier
            .height(TopBarHeight)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { onSettingClick() }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "setting button"
                )
            }
        }
    }
}
private val TopBarHeight = 56.dp

@Preview
@Composable
fun TimerTopBarPreview(){
    TimerTopBar()
}