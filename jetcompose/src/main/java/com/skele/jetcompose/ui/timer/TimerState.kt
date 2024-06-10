package com.skele.jetcompose.ui.timer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt

class TimerState(
    pomo : Int,
    short : Int,
    long : Int
) {
    var time by mutableIntStateOf(pomo)
        private set
    var isPaused by mutableStateOf(true)
        private set
    private val longbreakTerm = 4
    var pomoCount by mutableIntStateOf(0)
        private set
    var type by mutableStateOf(TimerType.POMODORO)
        private set

    var pomodoro by mutableIntStateOf(pomo)
        private set
    var shortBreak by mutableIntStateOf(short)
        private set
    var longBreak by mutableIntStateOf(long)
        private set
    var runningTimer : Job? = null
    fun updateFromInput(type: TimerType, value : String){
        if(value.isBlank()) return
        try{
            val parsedValue = parseInt(value)
            when(type){
                TimerType.POMODORO -> pomodoro = parsedValue
                TimerType.SHORT_BREAK -> shortBreak = parsedValue
                else -> longBreak = parsedValue
            }
            if(isPaused && this.type == type) time = parsedValue
        } catch (e : Exception){
            Log.d("TAG", "updateFromInput: InputFormat exception with $value")
        }
    }
    fun startTickDown(){
        runningTimer?.cancel()
        runningTimer = CoroutineScope(Dispatchers.Default).launch {
            tickDown()
        }
    }
    suspend fun tickDown() {
        while(time > 0 && !isPaused){
            time--;
            delay(1000)
        }
        if(time <= 0){
            moveToNext()
        }
    }
    private fun moveToNext() {
        if(type == TimerType.POMODORO) {
            pomoCount = (pomoCount++) % longbreakTerm

            if(pomoCount == 0){
                changeTimeType(TimerType.LONG_BREAK)
            } else {
                changeTimeType(TimerType.SHORT_BREAK)
            }
        } else {
            changeTimeType(TimerType.POMODORO)
        }
    }
    fun changeTimeType(type : TimerType) {
        this.type = type
        time = when(type){
            TimerType.POMODORO -> pomodoro
            TimerType.SHORT_BREAK -> shortBreak
            else -> longBreak
        }
        pause()
    }
    fun pause() {
        isPaused = true
    }
    fun resume() {
        isPaused = false
        startTickDown()
    }
}

enum class TimerType{
    POMODORO,
    SHORT_BREAK,
    LONG_BREAK
}