package com.skele.jetpack.main.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.skele.jetpack.data.Memo
import com.skele.jetpack.data.MemoRepository
import java.util.Date

class WriteViewModel(private val handle: SavedStateHandle) : ViewModel() {
    var id = handle.get<Long>("id") ?: -1
        private set(value : Long){
            handle["id"] = value
            field = value
        }
    fun setId(value : Long){
        id = value
        writeMode = WriteMode.EDIT
    }
    private var _memo = MutableLiveData<Memo>()
    val memo : LiveData<Memo> get() = _memo
    fun setMemo(value : Memo){
        _memo.value = value
    }
    var writeMode = handle.get<WriteMode>("write_mode") ?: WriteMode.WRITE
        private set(value : WriteMode){
            handle["write_mode"] = value
            field = value
        }
    private var _date = MutableLiveData<Long>()
    val date : LiveData<Long> get() = _date
    fun setDate(value : Long){
        _date.value = value
    }
}