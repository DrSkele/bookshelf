package com.skele.bookshelf.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.skele.bookshelf.MainActivity
import com.skele.bookshelf.Manifest
import com.skele.bookshelf.R
import com.skele.bookshelf.databinding.BottomsheetContentBinding
import com.skele.bookshelf.databinding.FragmentTaskBinding
import com.skele.bookshelf.permission.PermissionChecker
import com.skele.bookshelf.recyclerview.TaskAdapter
import com.skele.bookshelf.service.TaskSqliteService
import com.skele.bookshelf.sqlite.Task
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import java.util.Calendar
import java.util.Locale

private const val TAG = "TaskFragment"

class TaskFragment private constructor() : BaseFragment<FragmentTaskBinding>(FragmentTaskBinding::inflate) {

    // Reference to the holder activity
    // Because fragment cannot exist without an activity, it's guaranteed to access the host activity.
    private lateinit var activity: MainActivity

    // Recycler View Adapter
    private lateinit var adapter : TaskAdapter

    // BottomSheet
    private var _bottomSheetBinding : BottomsheetContentBinding? = null
    private val bottomSheetBinding get() = _bottomSheetBinding!!
    private lateinit var bottomSheet : BottomSheetDialog
    private var isEdit : Boolean = false
    private var editItem : Task? = null
    private var calendar : Calendar = Calendar.getInstance(Locale.KOREA)

    // Permission
    private lateinit var permissionChecker : PermissionChecker
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requiredPermissions = arrayOf(android.Manifest.permission.POST_NOTIFICATIONS)

    // Database Service
    private lateinit var dbService:TaskSqliteService
    private var isBound = false;
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TaskSqliteService.ServiceBinder
            dbService = binder.getService()
            adapter.submitList(dbService.selectAll())
            isBound = true;
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false;
        }
    }

    /**
     * Initializes fragment views in this fragment.
     * Registration for event listeners are done here.
     */
    private fun initView(){
        adapter = TaskAdapter()
        adapter.setOnListItemClickListener{
            openBottomSheet(it)
        }
        adapter.setOnItemContextClickListener{ menu, view, menuIten, item ->
            val menuItem = menu.add("Delete")
            menuItem?.setOnMenuItemClickListener {
                dbService.delete(item)
                adapter.submitList(dbService.selectAll())
                false
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.toolbar.setOnMenuItemClickListener {item->
            when(item.itemId){
                R.id.add_button -> {
                    openBottomSheet()
                }
            }
            true
        }
    }

    /**
     * Initializes bottom sheet used in this fragment.
     * BottomSheet contains input forms for creating new item or updating item.
     */
    private fun initBottomSheet(){
        bottomSheet = BottomSheetDialog(activity)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.dismissWithAnimation = true
        bottomSheetBinding.saveButton.setOnClickListener{
            saveTask()
        }
        bottomSheetBinding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }
    }

    /**
     * Saves item using information in the bottomsheet.
     * Creates new item or Updates one depending on whether item was given when opening bottomsheet.
     */
    private fun saveTask(){
        val title = bottomSheetBinding.titleEditText.text.toString()
        val description = bottomSheetBinding.descEditText.text.toString()
        if(title.isBlank()){
            bottomSheetBinding.titleInputLayout.error = "title text required"
        } else {
            bottomSheetBinding.titleInputLayout.error = ""

            if(isEdit){
                editItem?.let{
                    dbService.update(it.copy(title = title, description = description, dueDate = calendar.timeInMillis))
                }
            } else {
                val createdTask = Task(0, title, description, calendar.timeInMillis)
                dbService.insert(createdTask)
            }
            val list = dbService.selectAll()
            Log.d(TAG, "saveTask: $list")
            adapter.submitList(list)
            //adapter.notifyDataSetChanged()

            bottomSheet.dismiss()
        }
    }
    /**
     * Opens bottomsheet with given item.
     * If none was given, creates new on saving.
     */
    fun openBottomSheet(item : Task? = null){

        isEdit = item != null
        editItem = item
        bottomSheetBinding.titleEditText.setText(item?.title ?: "")
        bottomSheetBinding.descEditText.setText(item?.description ?: "")
        bottomSheetBinding.calendarView.date = item?.dueDate ?: Calendar.getInstance(Locale.KOREA).timeInMillis

        bottomSheet.show()
    }

    private fun checkPermission() : Boolean{
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permissionChecker.checkPermission(activity, requiredPermissions)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionChecker = PermissionChecker(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bottomSheetBinding = BottomsheetContentBinding.inflate(inflater, container, false)

        checkPermission()

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBottomSheet()
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
            isBound = false
        }
    }
    companion object {
        /**
         * Supply arguments with setArguments and later retrieved by the Fragment with getArguments.
         * These arguments are automatically saved and restored alongside the Fragment.
         * Later, restore fragment using bundle provided in the arguments.
         */
        @JvmStatic
        fun newInstance(param1: String) = TaskFragment()
    }
}