package com.skele.jetpack

import android.app.Application
import com.skele.jetpack.data.MemoRepository

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        MemoRepository.initialize(this)
    }
}