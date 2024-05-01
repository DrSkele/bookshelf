package com.skele.bookshelf

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.skele.bookshelf.service.TaskNotificationService

object TaskNotification {
    const val CHANNEL_ID = "task_notification_channel"
    const val CHANNEL_NAME = "Task_Notification"
    fun create(context : Context): Notification {

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val endIntent = Intent(context, TaskNotificationService::class.java)
        endIntent.action = NotificationActions.STOP
        val endPendingIntent = PendingIntent.getService(context, 0, endIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Task")
                .setContentText("Check Task")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true) // does not remove the notification on swipe or touch
                // changed behaviour on Android 14 :  https://developer.android.com/about/versions/14/behavior-changes-all?hl=ko#non-dismissable-notifications
                .addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_menu_close_clear_cancel,
                        "Close",
                        endPendingIntent
                    )
                )
                .setContentIntent(pendingIntent)
                .build()

        return notification
    }

}