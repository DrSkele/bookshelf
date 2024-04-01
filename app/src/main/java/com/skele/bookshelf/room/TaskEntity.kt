package com.skele.bookshelf.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

//https://developer.android.com/training/data-storage/room/defining-data
/**
 * Entity represents table in Room database
 */
@Entity(tableName = "task")
data class TaskEntity(
    // Entity class must have at least one PrimaryKey field
    // autoGenerate equals autoIncrement in SQL
    @PrimaryKey(autoGenerate = true)val id : Long,
    // Room uses field name as column name as default.
    // Use `@ColumnInfo(name = "column name")` to change column name in database.
    // Table and column name in database are case-insensitive
    val priority: Int,
    val task: String,
    val description: String?,
    val reg: Date,
    val due: Date?,
    // If there's a field you don't want to persist,
    // `@Ignore` annotation will exclude that field on column creation.
){
    constructor(priority: Int, task: String, description: String?, due: Date?) : this(
        // When autoGenerate is set to true on primary key,
        // insert method treats 0 or null as 'not-set' while inserting the item.
        id = 0,
        priority = priority,
        task = task,
        description = description,
        reg = Calendar.getInstance().time,
        due = due,
    )
}
