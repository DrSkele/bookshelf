package com.skele.bookshelf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skele.bookshelf.fragment.TaskFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //

    }


    /**
     * [androidx.fragment.app.FragmentManager] class is responsible for performing actions on the app's fragments,
     * such as adding, removing, or replacing them and adding them to the back stack.
     *
     * ### Activity-level
     *
     * FragmentActivity and its subclasses, such as AppCompatActivity, have access to the FragmentManager through the **`getSupportFragmentManager()`** method.
     *
     * ### Fragment-level
     *
     * Fragments can host one or more child fragments.
     * Get a reference to the FragmentManager that manages the fragment's children through **`getChildFragmentManager()`** inside the fragment.
     * Use **`getParentFragmentManager()`** to access its host FragmentManager.
     */
    fun addFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, TaskFragment.newInstance("parameter"))
            .commit()
    }

    /**
     * Requires androidx.fragment 1.4.0 or higher to handle multiple back stack
     */
    fun saveBackStack(){
        supportFragmentManager.saveBackStack("replacement")
    }
    fun restoreBackStack(){
        supportFragmentManager.restoreBackStack("replacement")
    }
}