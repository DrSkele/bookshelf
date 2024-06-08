package com.skele.jetcompose.ui.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class TimerState(
    initialType : TimerType,
    initialTime : Int
) {

    init {
        // TODO: time initial setting
    }

    var time by mutableIntStateOf(initialTime)
        private set
    var isPaused by mutableStateOf(true)
        private set
    var type by mutableStateOf(initialType)
        private set
    suspend fun tickDown() {
        while(time > 0 && !isPaused){
            delay(1000)
            time--;
        }
        if(time <= 0){
            reset()
        }
    }
    fun changeTimeType(type : TimerType) {
        this.type = type
        when(type){
            TimerType.POMODORO -> time = 1500
            TimerType.SHORT_BREAK -> time = 300
            else -> time = 900
        }
        pause()
    }
    fun reset() {
        changeTimeType(type)
    }
    fun pause() {
        isPaused = true
    }
    fun resume() {
        isPaused = false
    }

    companion object{
        val Saver : Saver<TimerState, *> = mapSaver(
            save = { mapOf( "type" to it.type.toString(), "time" to it.time) },
            restore = {
                TimerState(
                    initialType = TimerType.valueOf(it["type"] as String),
                    initialTime = it["time"] as Int
                )
            }
        )
    }
}

@Composable
fun rememberTimerState(
    type : TimerType,
    time : Int
) = rememberSaveable(stateSaver = TimerState.Saver) {
    mutableStateOf(TimerState(type, time))
}

enum class TimerType{
    POMODORO,
    SHORT_BREAK,
    LONG_BREAK
}