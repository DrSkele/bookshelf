package com.skele.bookshelf.service

import android.app.Service
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.skele.bookshelf.sqlite.Task
import com.skele.bookshelf.sqlite.TaskSqliteDao

private const val TAG = "TaskSqliteService"

/**
 * Service for sqlite database.
 *
 * CAUTION : By default, service runs on main thread of the application.
 *           Thus, it's synchronous with any context that has access to it.
 *           If asynchronous operation is required, it must be done using a new thread.
 */
class TaskSqliteService : Service() {

    private lateinit var database: TaskSqliteDao

    fun selectAll() : MutableList<Task>{
        return database.selectAll().toMutableList()
    }

    fun insert(item : Task) : Task? {
        return database.insert(item)
    }

    fun update(item: Task) {
        database.update(item)
    }

    fun delete(item: Task){
        database.delete(item)
    }

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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
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