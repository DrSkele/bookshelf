package com.skele.bookshelf.service

import android.app.Service
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.skele.bookshelf.sqlite.TaskSqliteDao

private const val TAG = "TaskSqliteService"
class TaskSqliteService : Service() {

    lateinit var database: TaskSqliteDao

    inner class ServiceBinder : Binder(){
        fun getService() : TaskSqliteService {
            return this@TaskSqliteService
        }
    }

    //service lifecycle
    override fun onCreate() {
        super.onCreate()
        database = TaskSqliteDao(this, TaskSqliteDao.DB_NAME, null, 1)
        Log.d(TAG, "onCreate: $database")
    }

    override fun onBind(intent: Intent): IBinder {
        return ServiceBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}