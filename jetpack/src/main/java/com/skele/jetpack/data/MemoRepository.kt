package com.skele.jetpack.data

import android.content.Context
import androidx.room.Room

/**
 * ### Database Repository
 * A singleton class wrapping the database.
 * - Database is accessed through the repository.
 * - Data can be fetched from a database in the repository, or from network if necessary.
 *
 * 데이터 접근을 위한 Repository
 * - Repository를 통해 데이터에 접근.
 * - 데이터를 어디에서 가져오는 경로를 필요에 따라 데이터베이스와 네트워크 중에서 선택할 수 있다.
 */
class MemoRepository private constructor(context : Context){
    private val database : MemoDatabase = Room.databaseBuilder(
        context.applicationContext,
        MemoDatabase::class.java,
        "memo.db"
    ).build()

    private val memoDao = database.memoDao()

    suspend fun getAllMemos() : List<Memo>{
        return memoDao.getAll()
    }
    suspend fun getMemo(id : Long) : Memo{
        return memoDao.get(id)
    }
    suspend fun insertMemo(item : Memo) : Long{
        return memoDao.insert(item)
    }
    suspend fun updateMemo(item : Memo){
        memoDao.update(item)
    }
    suspend fun delete(item : Memo){
        memoDao.delete(item)
    }
    suspend fun delete(id : Long){
        memoDao.delete(id)
    }

    companion object{
        private var _instance : MemoRepository? = null
        val instance get() = _instance!!
        fun initialize(context : Context){
            if(_instance == null) _instance = MemoRepository(context)
        }
    }
}