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
import com.skele.jetcompose.ui.timer.TimerState
import com.skele.jetcompose.ui.timer.TimerType

@Composable
fun SettingScreen(
    timerState: TimerState,
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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ){
            RowTimeInput(
                title = "Pomodoro",
                value =  timerState.pomodoro.toString(),
                onValueChanged = {value -> timerState.updateFromInput(TimerType.POMODORO, value)}
            )
            RowTimeInput(
                title = "Short Break",
                value = timerState.shortBreak.toString(),
                onValueChanged = {value -> timerState.updateFromInput(TimerType.SHORT_BREAK, value)}
            )
            RowTimeInput(
                title = "Long Break",
                value = timerState.longBreak.toString(),
                onValueChanged = {value -> timerState.updateFromInput(TimerType.LONG_BREAK, value)}
            )
        }
    }
}

@Composable
fun RowTimeInput(
    title : String,
    value : String,
    onValueChanged : (value : String) -> Unit = {}
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
            value = value,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
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
    SettingScreen(TimerState(1500, 300, 900))
}

@Preview
@Composable
fun RowTimeInputPreview(){
    RowTimeInput(title = "title", "10")
}