package com.skele.bookshelf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skele.bookshelf.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private val binding : ActivityTaskBinding by lazy{
        ActivityTaskBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}