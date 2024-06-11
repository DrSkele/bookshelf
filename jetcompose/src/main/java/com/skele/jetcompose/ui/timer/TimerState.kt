package com.skele.jetcompose.ui.timer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt

class TimerState(
    pomo : Int,
    short : Int,
    long : Int
) {
    private var _timeFlow = MutableStateFlow(pomo)
    val timeFlow : StateFlow<Int> = _timeFlow.asStateFlow()
    private var _isPaused = MutableStateFlow(true)
    var isPaused : StateFlow<Boolean> = _isPaused.asStateFlow()
    private var _type = MutableStateFlow(TimerType.POMODORO)
    var type : StateFlow<TimerType> = _type.asStateFlow()
    private val longbreakTerm = 4
    var pomoCount by mutableIntStateOf(0)
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
            if(isPaused.value && this.type.value == type) _timeFlow.value = parsedValue
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
        while(_timeFlow.value > 0 && !isPaused.value){
            _timeFlow.value--;
            delay(1000)
        }
        if(_timeFlow.value <= 0){
            moveToNext()
        }
    }
    private fun moveToNext() {
        if(type.value == TimerType.POMODORO) {
            pomoCount = (pomoCount+1) % longbreakTerm
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
        _type.value = type
        _timeFlow.value = when(type){
            TimerType.POMODORO -> pomodoro
            TimerType.SHORT_BREAK -> shortBreak
            else -> longBreak
        }
        pause()
    }
    fun pause() {
        _isPaused.value = true
    }
    fun resume() {
        _isPaused.value = false
        startTickDown()
    }
}

enum class TimerType(val text : String){
    POMODORO("Pomodoro"),
    SHORT_BREAK("Short break"),
    LONG_BREAK("Long break")
}