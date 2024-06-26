package com.skele.jetpack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * ### Dao
 * Data access object for specific entity.
 * - Dao is declared as an interface, and the framework generates its implementation.
 *
 * Entity 테이블에 접근하기 위한 Dao.
 * - Dao는 인터페이스로 선언되며 구현체는 프레임워크에 의해 생성된다.
 */
@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getAll() : LiveData<List<Memo>>
    @Query("SELECT * FROM memo WHERE id = (:id)")
    fun get(id : Long) : LiveData<Memo>
    @Query("SELECT * FROM memo WHERE id = (:id)")
    suspend fun select(id : Long) : Memo?
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item : Memo) : Long
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(items : List<Memo>) : List<Long>
    @Update
    suspend fun update(item : Memo)
    @Delete
    suspend fun delete(item : Memo)
    @Query("DELETE FROM memo WHERE id = (:id)")
    suspend fun delete(id : Long)
}