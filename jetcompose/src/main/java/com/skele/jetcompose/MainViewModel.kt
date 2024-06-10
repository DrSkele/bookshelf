package com.skele.jetcompose

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.skele.jetcompose.ui.timer.TimerState

class MainViewModel(
    private val savedHandle : SavedStateHandle
) : ViewModel() {
    val timerState = TimerState(1500, 300, 900)
}