package com.skele.bookshelf.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update

//https://developer.android.com/training/data-storage/room/accessing-data
/**
 * Data Access Object provides methods to access database.
 * Room automatically generates implementations of DAOs
 */
@Dao
@TypeConverters(DateConverter::class)
interface TaskDao {
    /**
     * Returns all rows in the table.
     */
    @Query("SELECT id, priority, task, description, reg, due FROM task")
    fun getAll() : List<TaskEntity>

    /**
     * Takes an Entity as a parameter.
     * Inserts the entity into the corresponding table in the database.
     *
     * @param task Instance of a Room data entity class annotated with `@Entity`
     * @return new row id of the inserted entity
     */
    @Insert
    fun insert(task : TaskEntity)
    /**
     * Takes an Entity as a parameter.
     * Updates the row in database matching the primary key in the passed entity.
     * If there's no match, no change is made.
     *
     * @param task only uses primary key of this entity
     * @return number of rows affected
     */
    @Update
    fun update(task: TaskEntity)
    /**
     * Takes an Enitiy as a parameter.
     * Deletes the row in database matching the primary key in the passed entity.
     * If there's no match, no change is made.
     *
     * @param task only uses primary key of this entity
     * @return number of rows affected
     */
    @Delete
    fun delete(task: TaskEntity)

    /**
     * Deletes all row from the table.
     */
    @Query("DELETE FROM task")
    fun deleteAll()
}