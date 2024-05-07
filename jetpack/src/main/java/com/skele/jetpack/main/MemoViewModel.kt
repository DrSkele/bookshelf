package com.skele.jetpack.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skele.jetpack.data.Memo
import com.skele.jetpack.data.MemoRepository
import kotlinx.coroutines.launch

/**
 * ### ViewModel
 * Logic & State holder for the activity
 * - Lifecycle of the ViewModel is longer than that of activity or fragment.
 * - ViewModel holds its data until activity finishes normally or fragment detaches.
 * - Data is kept on abnormal termination of activity or fragment for restoration on restart.
 *
 * 데이터 유지와 관리
 * - ViewModel의 생명주기는 activity나 fragment보다 길다.
 * - Activity가 Finish되거나 fragment가 Detach되기 전까지 ViewModel은 데이터를 유지.
 * - 비정상적인 종료나 Activity가 파괴되더라도 데이터가 유지되기 때문에 화면회전 등의 경우에도 대응할 수 있다.
 */
class MemoViewModel : ViewModel() {

    private val repository = MemoRepository.instance

    /**
     * ### LiveData
     * Lifecycle-aware observable data holder
     * - LiveData respects lifecycle of its observer, and will not update if observer is not active.
     * - Use #setValue() on main thread, and #postValue() on worker thread.
     *
     * 관찰가능하고 생명주기가 관리 가능한 데이터
     * - LiveData는 활성화되어 있는 관찰자에게만 데이터를 갱신
     * - 메인 스레드에서는 #setValue()를 사용하고, 작업 스레드에서는 #postValue()를 사용해야 한다.
     */
    val memoList : LiveData<List<Memo>> = repository.getAllMemos()

    fun getMemo(id : Long) : LiveData<Memo>{
        return repository.getMemo(id)
    }
    suspend fun selectMemo(id : Long) : Memo?{
        return repository.selectMemo(id)
    }
    fun insert(item : Memo){
        viewModelScope.launch {
            repository.insertMemo(item)
        }
    }
    fun update(item: Memo){
        viewModelScope.launch {
            repository.updateMemo(item)
        }
    }
    fun delete(item: Memo){
        viewModelScope.launch {
            repository.delete(item)
        }
    }
    fun delete(id : Long){
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}