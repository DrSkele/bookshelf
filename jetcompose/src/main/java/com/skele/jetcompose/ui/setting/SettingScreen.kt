package com.skele.jetcompose.ui.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingScreen(
    onBackPressed : () -> Unit = {}
){
    Scaffold(
        topBar = {
            SettingTopBar(
                onBackPressed = {
                    onBackPressed()
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ){

        }
    }
}