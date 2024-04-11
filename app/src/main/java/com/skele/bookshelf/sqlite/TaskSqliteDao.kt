package com.skele.bookshelf.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class TaskSqliteDao(context: Context, dbName: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, dbName, factory, version){
    companion object{
        const val DB_NAME = "task_sqlite.db"
    }
    private val TABLE_NAME = Task::class.java.simpleName
    private val TASK_ID = Task::id.name
    private val TASK_PRIORITY = Task::priority.name
    private val TASK_TITLE = Task::title.name
    private val TASK_DESCRIPTION = Task::description.name
    private val TASK_REG_DATE = Task::regDate.name
    private val TASK_DUE_DATE = Task::dueDate.name

    lateinit var database: SQLiteDatabase

    // Creation of table.
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("""
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TASK_PRIORITY TEXT,
                $TASK_TITLE TEXT,
                $TASK_DESCRIPTION TEXT,
                $TASK_REG_DATE LONG,
                $TASK_DUE_DATE LONG
            );
        """.trimIndent())
    }


    // CAUTION : onOpen is called when the database is 'opened', not on 'created'.
    //           Database is opened when 'getReadableDatabase()' or 'getWritableDatabase()' is called.
    //           It's not possible to gain reference of database here and use it on query or any SQL execution, because database does not open until that execution happens.
    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
    }

    // Called on version upgrade.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("""
            DROP TABLE IF EXISTS $TABLE_NAME
        """.trimIndent())
    }

    fun select(id : Long) : Task? {
        val columns = arrayOf(TASK_ID, TASK_PRIORITY, TASK_TITLE, TASK_DESCRIPTION, TASK_REG_DATE, TASK_DUE_DATE)
        val where = "$TASK_ID = ?"
        var task : Task? = null
        database.query(TABLE_NAME, columns, where, arrayOf(id.toString()), null, null, null, null)
        .use { cursor ->
            if(cursor.moveToNext()){
                task = Task(
                    id = cursor.getLong(0),
                    priority = cursor.getInt(1),
                    title = cursor.getString(2),
                    description =  cursor.getString(3),
                    regDate = cursor.getLong(4),
                    dueDate = cursor.getLong(5),
                )
            }
        }
        return task
    }

    fun selectAll() : List<Task> {
        val list = mutableListOf<Task>()
        readableDatabase.rawQuery("""
            SELECT $TASK_ID, $TASK_PRIORITY, $TASK_TITLE, $TASK_DESCRIPTION, $TASK_REG_DATE, $TASK_DUE_DATE
            FROM $TABLE_NAME
        """.trimIndent(), null)
        .use { cursor ->
            while (cursor.moveToNext()){
                list.add(
                    Task(
                        id = cursor.getLong(0),
                        priority = cursor.getInt(1),
                        title = cursor.getString(2),
                        description =  cursor.getString(3),
                        regDate = cursor.getLong(4),
                        dueDate = cursor.getLong(5),
                    )
                )
            }
        }
        return list
    }

    fun insert(item : Task){
        val contentValues = ContentValues().apply {
            put(TASK_PRIORITY, item.priority)
            put(TASK_TITLE, item.title)
            put(TASK_DESCRIPTION, item.description)
            put(TASK_REG_DATE, item.regDate)
            put(TASK_DUE_DATE, item.dueDate)
        }
        database.beginTransaction()
        database.insert(TABLE_NAME, null, contentValues)
        database.endTransaction()
    }

    fun update(item : Task){
        val contentValues = ContentValues().apply {
            put(TASK_PRIORITY, item.priority)
            put(TASK_TITLE, item.title)
            put(TASK_DESCRIPTION, item.description)
            put(TASK_DUE_DATE, item.dueDate)
        }
        val where = "$TASK_ID = ?"
        database.beginTransaction()
        database.update(TABLE_NAME, contentValues, where, arrayOf(item.id.toString()))
        database.endTransaction()
    }

    fun delete(item : Task){
        val where = "$TASK_ID = ?"
        database.beginTransaction()
        database.delete(TABLE_NAME, where, arrayOf(item.id.toString()))
        database.endTransaction()
    }

}