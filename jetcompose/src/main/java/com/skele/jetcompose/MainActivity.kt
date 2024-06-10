package com.skele.jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skele.jetcompose.ui.setting.SettingScreen
import com.skele.jetcompose.ui.theme.BookShelfTheme
import com.skele.jetcompose.ui.timer.TimerScreen
import com.skele.jetcompose.ui.timer.TimerState
import com.skele.jetcompose.util.navigateSingleTopTo


class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel.timerState)
        }
    }
}

@Composable
fun MyApp(
    timerState: TimerState
) {
    val navController = rememberNavController()
    BookShelfTheme{
        NavHost(
            navController = navController,
            startDestination = TimerPage.route,
        ) {
            composable(route = TimerPage.route) {
                TimerScreen(
                    timerState = timerState,
                    onOpenSetting = { navController.navigateSingleTopTo(SettingPage.route) }
                )
            }
            composable(route = SettingPage.route) {
                SettingScreen(
                    timerState = timerState,
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}


@Preview
@Composable
fun MyAppPreview() {
    BookShelfTheme {
        MyApp(timerState = TimerState(1500, 300, 900))
    }
}