package com.skele.jetcompose.ui.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Integer.parseInt

@Composable
fun SettingScreen(
    onBackPressed : () -> Unit = {}
){
    val settingState = SettingsState(
        initialPomoTime = 1500,
        initialShortTime = 300,
        initialLongTime = 900
    )

    Scaffold(
        topBar = {
            SettingTopBar(
                onBackPressed = {
                    onBackPressed()
                }
            )
        }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ){
            RowTimeInput(
                title = "Pomodoro",
                settingState.pomodoro
            )
            RowTimeInput(
                title = "Short Break",
                settingState.shortBreak
            )
            RowTimeInput(
                title = "Long Break",
                settingState.longBreak
            )
        }
    }
}

@Composable
fun RowTimeInput(
    title : String,
    state : TimeInputState
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 24.sp,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        OutlinedTextField(
            value = state.time.toString(),
            onValueChange = { value -> state.updateTime(parseInt(value)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = null),
            singleLine = true,
            textStyle = TextStyle(textAlign = TextAlign.End, fontSize = 20.sp),
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun SettingScreenPreview(){
    SettingScreen()
}

@Preview
@Composable
fun RowTimeInputPreview(){
    RowTimeInput(title = "title", TimeInputState(0))
}