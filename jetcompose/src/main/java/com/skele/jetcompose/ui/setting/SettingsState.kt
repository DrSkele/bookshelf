package com.skele.jetcompose.ui.setting

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import java.lang.Integer.parseInt

class SettingsState(
    private val initialPomoTime : Int,
    private val initialShortTime : Int,
    private val initialLongTime : Int
) {
    val pomodoro = MinuteState(initialPomoTime)
    val shortBreak = MinuteState(initialShortTime)
    val longBreak = MinuteState(initialLongTime)
}

class MinuteState(private val initialValue : Int){
    var time by mutableIntStateOf(initialValue)
        private set

    fun updateFromInput(value : String){
        if(value.isBlank()) return
        try{
            time = parseInt(value)
        } catch (e : Exception){
            Log.d("TAG", "updateFromInput: InputFormat exception with $value")
        }
    }
    fun tickDown(){
        time--;
    }
    fun tickUp(){
        time++;
    }
}

