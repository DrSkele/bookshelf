package com.skele.http

import android.app.Application
import com.google.gson.GsonBuilder
import com.skele.http.data.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    private final val base_url = "https://dapi.kakao.com/v3/search/"
    override fun onCreate() {
        super.onCreate()

        val gson = GsonBuilder()
            .setLenient() // makes json conversion process loose.
            .create()

        val client : OkHttpClient = OkHttpClient().newBuilder()
            .readTimeout(5_000, TimeUnit.MILLISECONDS)
            .connectTimeout(5_000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // interceptor enables log of http transitions
            .addInterceptor(HeaderInterceptor()) // interceptor for adding auth header
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    companion object{
        // Recommended use of retrofit is to make it a singleton.
        // Retrofit은 싱글톤으로 만드는 것이 추천된다
        lateinit var retrofit : Retrofit
    }
}