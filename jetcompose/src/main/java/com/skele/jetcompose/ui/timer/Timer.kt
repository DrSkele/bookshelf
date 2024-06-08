package com.skele.jetcompose.ui.timer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.skele.jetcompose.ui.components.SelectorButton
import com.skele.jetcompose.ui.components.SelectorGroup
import com.skele.jetcompose.ui.components.rememberSelectorState

@Composable
fun Timer(
    modifier: Modifier
){
    val timerState by rememberTimerState(type = TimerType.POMODORO, time = 1500)
    val selectorState by rememberSelectorState(initialValue = TimerType.POMODORO.toString())

    LaunchedEffect(key1 = timerState.isPaused) {
        timerState.tickDown()
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SelectorGroup(
                selectorState = selectorState,
                onSelectionChange = { id ->
                    Log.d("TAG", "Timer: $id")
                    timerState.changeTimeType(TimerType.valueOf(id))
                }
            ){
                SelectorButton(
                    text = TimerType.POMODORO.toString(),
                    selectorState = selectorState
                )
                SelectorButton(
                    text = TimerType.SHORT_BREAK.toString(),
                    selectorState = selectorState
                )
                SelectorButton(
                    text = TimerType.LONG_BREAK.toString(),
                    selectorState = selectorState
                )
            }
            CountdownTimer(
                time = timerState.time,
                isPaused = timerState.isPaused,
                onPause = { timerState.pause() },
                onResume = { timerState.resume() }
            )
        }
    }
}

@Composable
fun CountdownTimer(
    time : Int,
    isPaused : Boolean,
    onPause : () -> Unit = {},
    onResume : () -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(time.toString(), fontSize = 40.sp)
        if(isPaused){
            Button(
                onClick = { onResume() }
            ) {
                Text("Start")
            }
        } else {
            Button(
                onClick = { onPause() }
            ) {
                Text("Pause")
            }
        }
    }
}