package com.skele.jetpack.main.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skele.jetpack.databinding.DialogCalendarBinding
import com.skele.jetpack.main.list.OnItemClickListener
import com.skele.jetpack.util.BaseBottomSheetDialogFragment
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarDialog() : BaseBottomSheetDialogFragment<DialogCalendarBinding>(DialogCalendarBinding::inflate){

    private var date : Date = Date()
    private fun initData(){
        arguments?.getLong(KEY_DATE_TIME)?.let {
            date = Date(it)
        }
    }
    private fun initView(){
        binding.calendarViewDialog.date = date.time
        binding.calendarViewDialog.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = Date(year, month, dayOfMonth)
        }
        binding.buttonDialogOk.setOnClickListener{
            setFragmentResult(KEY_DATE_TIME, bundleOf(KEY_DATE_TIME to date.time))
            parentFragmentManager.popBackStack()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    companion object{
        const val KEY_DATE_TIME = "calendar_date_time"
    }
}