package com.skele.bookshelf.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.skele.bookshelf.MainActivity
import com.skele.bookshelf.databinding.FragmentTaskBinding
import com.skele.bookshelf.recyclerview.TaskAdapter
import com.skele.bookshelf.service.TaskSqliteService

private const val ARG_PARAM1 = "param1"
private const val TAG = "TaskFragment"

class TaskFragment private constructor() : BaseFragment<FragmentTaskBinding>(FragmentTaskBinding::inflate) {

    private var param1: String? = null

    // Reference to the holder activity
    // Because fragment cannot exist without an activity, it's guaranteed to access the host activity.
    lateinit var activity: MainActivity

    lateinit var adapter : TaskAdapter

    private lateinit var dbService:TaskSqliteService
    private var isBound = false;
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TaskSqliteService.ServiceBinder
            dbService = binder.getService()
            Log.d(TAG, "onServiceConnected: ${dbService.database}")
            adapter.list = dbService.database.selectAll()
            adapter.notifyDataSetChanged()
            isBound = true;
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false;
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get field variables from the argument
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TaskAdapter(listOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.button.setOnClickListener{
            adapter.list = dbService.database.selectAll()
        }
    }
    override fun onStart() {
        super.onStart()

        // connect the service
        if(isBound == false){
            val intent = Intent(activity, TaskSqliteService::class.java)
            activity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }
    override fun onStop() {
        super.onStop()

        // disconnect service
        if(isBound){
            activity.unbindService(serviceConnection)
        }
    }
    companion object {
        /**
         * Supply arguments with setArguments and later retrieved by the Fragment with getArguments.
         * These arguments are automatically saved and restored alongside the Fragment.
         * Later, restore fragment using bundle provided in the arguments.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            TaskFragment().apply {
                // send parameters to fragment through arguments
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}