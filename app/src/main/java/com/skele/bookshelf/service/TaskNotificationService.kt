package com.skele.bookshelf.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.skele.bookshelf.MainActivity
import com.skele.bookshelf.NotificationActions
import com.skele.bookshelf.TaskNotification
import com.skele.bookshelf.permission.PermissionChecker
import com.vmadalin.easypermissions.EasyPermissions

class TaskNotificationService : Service() {

    lateinit var builder: NotificationCompat.Builder

    private fun startForegroundService(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TaskNotification.CHANNEL_ID,
                TaskNotification.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            // 알림 등록
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = TaskNotification.create(this)
        startForeground(NOTIFICATION_ID, notification)
    }
    private fun endRoregroundService(){
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){
            NotificationActions.STOP -> {

            }
        }
        return START_STICKY
    }
    inner class ServiceBinder : Binder(){
        fun getService() : TaskNotificationService {
            return this@TaskNotificationService
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return ServiceBinder()
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object{
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_id_for_bookshelf"
    }
}