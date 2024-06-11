package com.skele.jetcompose

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skele.jetcompose.permission.PermissionChecker
import com.skele.jetcompose.service.CustomActions
import com.skele.jetcompose.service.TimerService
import com.skele.jetcompose.ui.setting.SettingScreen
import com.skele.jetcompose.ui.theme.BookShelfTheme
import com.skele.jetcompose.ui.timer.TimerScreen
import com.skele.jetcompose.ui.timer.TimerState
import com.skele.jetcompose.util.navigateSingleTopTo


class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by viewModels()

    private var timerService: TimerService? = null
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            viewModel.isServiceReady = true
            val binder = service as TimerService.TimerServiceBinder
            timerService = binder.getService()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            viewModel.isServiceReady = false
        }
    }
    fun startTimerService(){
        val intent = Intent(this, TimerService::class.java).apply {
            action = CustomActions.CREATE
        }
        startService(intent)
    }
    fun bindTimerService(){
        val intent = Intent(this, TimerService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }
    fun unbindTimerService(){
        if(viewModel.isServiceReady) unbindService(serviceConnection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(
                viewModel.isServiceReady,
                timerService?.timerState
            )
        }
        startTimerService()
    }

    override fun onStart() {
        super.onStart()
        bindTimerService()
    }

    override fun onStop() {
        super.onStop()
        unbindTimerService()
    }
}

@Composable
fun MyApp(
    isServiceReady : Boolean,
    timerState: TimerState?
) {
    val runtimePermissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    PermissionChecker.checkPermission(context = LocalContext.current, permissions = runtimePermissions)

    val navController = rememberNavController()
    BookShelfTheme{
        if(isServiceReady){
            NavHost(
                navController = navController,
                startDestination = TimerPage.route,
            ) {
                composable(route = TimerPage.route) {
                    TimerScreen(
                        timerState = timerState!!,
                        onOpenSetting = { navController.navigateSingleTopTo(SettingPage.route) }
                    )
                }
                composable(route = SettingPage.route) {
                    SettingScreen(
                        timerState = timerState!!,
                        onBackPressed = { navController.popBackStack() }
                    )
                }
            }
        } else {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BookShelfTheme {
        MyApp(true, timerState = TimerState(1500, 300, 900))
    }
}