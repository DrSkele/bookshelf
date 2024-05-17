package com.skele.http.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skele.http.data.Document
import com.skele.http.data.DocumentAPI
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private var _documentList = MutableLiveData<List<Document>>()
    val documentList : LiveData<List<Document>> get() = _documentList

    fun loadDocuments(page : Int, query : String){
        if(query.isBlank()) return

        viewModelScope.launch {
            val response = DocumentAPI.service.getDocuments(page, query = query)

            _documentList.value = response.documents
        }
    }
}