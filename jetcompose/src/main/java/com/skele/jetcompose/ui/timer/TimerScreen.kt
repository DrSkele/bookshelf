package com.skele.jetcompose.ui.timer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimerScreen(
    onOpenSetting : () -> Unit = {}
){
    Scaffold(
        topBar = {
            TimerTopBar(
                onSettingClick = {
                    onOpenSetting()
                }
            )
        }
    ) { padding ->
        Timer(modifier = Modifier.padding(padding))
    }
}

