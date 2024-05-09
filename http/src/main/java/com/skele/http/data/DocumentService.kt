package com.skele.http.data

import com.skele.http.ApplicationClass
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DocumentService {

    @GET
    suspend fun getDocuments(
        //@Header("authorization") apiKey : String,
        @Query("sort") sort : String,
        @Query("page") page : Int,
        @Query("target") target : String,
        @Query("query") query : String
    ) : ResponseData
}

object DocumentAPI{
    val service by lazy {
        ApplicationClass.retrofit.create(DocumentService::class.java)
    }
}