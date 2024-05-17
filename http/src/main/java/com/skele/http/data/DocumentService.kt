package com.skele.http.data

import com.skele.http.ApplicationClass
import com.skele.http.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DocumentService {

    @GET("book")
    suspend fun getDocuments(
        @Query("page") page : Int,
        @Query("size") size : Int = 10,
        @Query("query") query : String,
        @Query("sort") sort : String? = null,
        @Query("target") target : String? = null,
        @Header("Authorization") apiKey : String = "KakaoAK ${BuildConfig.API_KEY}",
    ) : ResponseData
}

object DocumentAPI{
    val service by lazy {
        ApplicationClass.retrofit.create(DocumentService::class.java)
    }
}