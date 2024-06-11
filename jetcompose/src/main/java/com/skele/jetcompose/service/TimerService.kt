package com.skele.jetcompose.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.skele.jetcompose.MainActivity
import com.skele.jetcompose.R
import com.skele.jetcompose.ui.timer.TimerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TimerService : LifecycleService() {

    val timerState = TimerState(1500, 300, 900)

    private val CHANNEL_ID = "foreground_timer"
    private val NOTIFICATION_ID = 99
    private var notificationBuilder : NotificationCompat.Builder? = null


    private lateinit var startPendingIntent: PendingIntent
    private lateinit var pausePendingIntent: PendingIntent

    fun createNotification() : Notification {
        val notificationBodyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val bodyPendingIntent = PendingIntent.getActivity(this, 0, notificationBodyIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        if(notificationBuilder == null) notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        val notification = notificationBuilder!!
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(timerState.type.value.text)
            .setContentText(timerState.timeFlow.value.toString())
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_play,
                    "Start",
                    startPendingIntent
                )
            )
            .setContentIntent(bodyPendingIntent)
            .setStyle(null)
            .build()
        return notification
    }
    fun updateNotification() : Notification {
        val notification = notificationBuilder!!.apply {
            setContentTitle(timerState.type.value.text)
            setContentText(timerState.timeFlow.value.toString())
            clearActions()
            addAction(
                if(timerState.isPaused.value){
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_play,
                        "Start",
                        startPendingIntent
                    )
                } else {
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_play,
                        "Pause",
                        pausePendingIntent
                    )
                }
            )
        }.build()
        return notification
    }
    fun startForegroundService(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Timer Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }

        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
    }
    fun updateForegroundService(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        lifecycleScope.launch {
            launch {
                timerState.timeFlow.collectLatest {
                    manager.notify(NOTIFICATION_ID, updateNotification())
                }
            }
            launch {
                timerState.type.collectLatest {
                    manager.notify(NOTIFICATION_ID, updateNotification())
                }
            }
            launch {
                timerState.isPaused.collectLatest {
                    manager.notify(NOTIFICATION_ID, updateNotification())
                }
            }
        }
    }
    private fun init(){
        val notificationStartIntent = Intent(this, TimerService::class.java).apply {
            action = CustomActions.START
        }
        val notificationPauseIntent = Intent(this, TimerService::class.java).apply {
            action = CustomActions.PAUSE
        }
        startPendingIntent = PendingIntent.getService(this, 0, notificationStartIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        pausePendingIntent = PendingIntent.getService(this, 0, notificationPauseIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    override fun onCreate() {
        super.onCreate()
        init()
        startForegroundService()
        updateForegroundService()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartCommand: ${intent?.action}")
        when(intent?.action){
            CustomActions.START -> {
                timerState.resume()
            }
            CustomActions.PAUSE -> {
                timerState.pause()
            }
        }
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return TimerServiceBinder()
    }

    inner class TimerServiceBinder : Binder(){
        fun getService() : TimerService = this@TimerService
    }
}