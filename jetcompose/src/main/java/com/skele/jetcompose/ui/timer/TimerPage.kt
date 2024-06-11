package com.skele.jetcompose.ui.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skele.jetcompose.ui.components.SelectorButton

@Composable
fun TimerPage(
    timerState: TimerState,
    modifier: Modifier
){
    Surface(modifier = modifier.fillMaxSize()) {
        val time by timerState.timeFlow.collectAsState()
        val isPaused by timerState.isPaused.collectAsState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                TimerType.entries.forEach {
                    SelectorButton(
                        text = it.toString(),
                        isSelected = it == timerState.type.value,
                        onSelect = { timerState.changeTimeType(it) }
                    )
                }
            }
            CountdownTimer(
                time = time,
                isPaused = isPaused,
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