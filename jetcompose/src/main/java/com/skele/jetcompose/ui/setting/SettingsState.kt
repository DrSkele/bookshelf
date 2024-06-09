package com.skele.jetcompose.ui.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class SettingsState(
    private val initialPomoTime : Int,
    private val initialShortTime : Int,
    private val initialLongTime : Int
) {
    val pomodoro = TimeInputState(initialPomoTime)
    val shortBreak = TimeInputState(initialShortTime)
    val longBreak = TimeInputState(initialLongTime)
}

class TimeInputState(private val initialValue : Int){
    var time by mutableIntStateOf(initialValue)
        private set

    fun updateTime(value : Int){
        time = value
    }
    fun tickDown(){
        time--;
    }
    fun tickUp(){
        time++;
    }
}

