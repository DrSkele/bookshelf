package com.skele.jetcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class SelectorState (
    initialValue : String
) {
    var selected by mutableStateOf(initialValue)
        private set

    fun select(value : String){
        selected = value
    }

    companion object{
        val Saver : Saver<SelectorState, *> = listSaver(
            save = { listOf(it.selected) },
            restore = {
                SelectorState(
                    initialValue = it[0]
                )
            }
        )
    }
}

@Composable
fun rememberSelectorState(
    initialValue: String
) = rememberSaveable(initialValue, stateSaver = SelectorState.Saver) {
    mutableStateOf(SelectorState(initialValue))
}