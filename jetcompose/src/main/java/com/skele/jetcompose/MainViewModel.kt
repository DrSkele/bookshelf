package com.skele.jetcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val savedHandle : SavedStateHandle
) : ViewModel() {
    var isServiceReady by mutableStateOf(false)
}