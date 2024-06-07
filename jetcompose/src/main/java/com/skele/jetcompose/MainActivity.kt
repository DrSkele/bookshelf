package com.skele.jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skele.jetcompose.ui.theme.BookShelfTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    BookShelfTheme{
        Scaffold(
            topBar = {
                TimerTopBar()
            }
        ) {padding ->
            TimerPage(modifier = Modifier.padding(padding))
        }
    }
}

@Composable
fun TimerTopBar(){
    Row(
        modifier = Modifier
            .height(TopBarHeight)
            .fillMaxWidth()
            .padding(8.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "setting button",
            modifier = Modifier
        )
    }
}
private val TopBarHeight = 56.dp
@Composable
fun TimerPage(modifier: Modifier){

    var time by rememberSaveable {
        mutableStateOf(1500)
    }
    var isPaused by rememberSaveable {
        mutableStateOf(true)
    }
    var selected by rememberSaveable {
        mutableStateOf(1)
    }

    LaunchedEffect(key1 = isPaused) {
        while(time > 0 && !isPaused){
            delay(1000)
            time--;
        }
        if(time <= 0){
            isPaused = true
            time = 10
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TimeSelector(
                idx = selected,
                onSelect = { idx ->
                    selected = idx
                    time = when(idx){
                        1 -> 1500
                        2 -> 300
                        else -> 900
                    }
                    isPaused = true
                }
            )
            CountdownTimer(
                time = time,
                isPaused = isPaused,
                onPause = { isPaused = true },
                onResume = { isPaused = false }
            )
        }
    }
}

@Composable
fun TimeSelector(
    idx : Int,
    onSelect : (idx : Int) -> Unit = {}
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SelectorButton(
            text = "Pomodoro",
            isSelected = idx == 1,
            onclick = { onSelect(1) }
        )
        SelectorButton(
            text = "Short Break",
            isSelected = idx == 2,
            onclick = { onSelect(2) }
        )
        SelectorButton(
            text = "Long Break",
            isSelected = idx == 3,
            onclick = { onSelect(3) }
        )
    }
}

@Composable
fun SelectorButton(
    text : String,
    isSelected : Boolean,
    onclick : () -> Unit = {}
){
    Surface(
        modifier = Modifier
            .clickable { onclick() }
    ) {
        Text(
            text,
            modifier = Modifier.background(color = if (isSelected) Color.Cyan else Color.Gray)
        )
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

@Preview
@Composable
fun MyAppPreview() {
    BookShelfTheme {
        MyApp()
    }
}