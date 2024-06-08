package com.skele.jetcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp

@Composable
fun SelectorGroup(
    selectorState: SelectorState,
    onSelectionChange : (id : String) -> Unit = {},
    content: @Composable() (RowScope.() -> Unit)
){
    val currentOnChange by rememberUpdatedState(newValue = onSelectionChange)
    LaunchedEffect(key1 = selectorState) {
        snapshotFlow{ selectorState.selected }
            .collect{
                currentOnChange(it)
            }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        content()
    }
}