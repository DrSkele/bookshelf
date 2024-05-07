package com.skele.jetpack.main.write

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.skele.jetpack.R
import com.skele.jetpack.data.Memo
import com.skele.jetpack.data.MemoRepository
import com.skele.jetpack.databinding.FragmentWriteBinding
import com.skele.jetpack.main.MemoViewModel
import com.skele.jetpack.util.BaseFragment
import com.skele.jetpack.util.toDateString
import kotlinx.coroutines.launch
import java.lang.System.exit
import java.util.Date


class WriteFragment : BaseFragment<FragmentWriteBinding>(FragmentWriteBinding::inflate) {
    private val memoViewModel : MemoViewModel by activityViewModels()
    private val writeViewModel : WriteViewModel by viewModels()

    private lateinit var bottomCalendar : CalendarDialog
    private fun initData(){
        writeViewModel.setDate(Date().time)
        arguments?.getLong(Memo::id.name)?.let { id ->
            writeViewModel.setId(id)
        }
    }
    private fun initView(item : Memo? = null){
        binding.apply {
            etWriteTitle.setText(item?.title ?: "")
            etWriteContent.setText(item?.title ?: "")

            switchUseDue.isChecked = item?.hasDue ?: false
            writeViewModel.setDate(item?.dueDate ?: Date().time)
        }
        // set calendar callback

    }
    private fun initListener(){
        binding.apply {
            switchUseDue.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) showCalendar()
            }
            boxWriteMemoDate.setOnClickListener {
                if(switchUseDue.isChecked) showCalendar()
            }
            toolBar.setNavigationOnClickListener {
                exit()
            }
            toolBar.setOnMenuItemClickListener {menu ->
                when(menu.itemId){
                    R.id.menu_btn_write -> {

                    }
                }
                true
            }
            writeViewModel.date.observe(viewLifecycleOwner){
                textWriteMemoDueDate.text = it.toString()
            }
        }
        writeViewModel.memo.observe(viewLifecycleOwner){memo ->
            initView(memo)
        }
        setFragmentResultListener(CalendarDialog.KEY_DATE_TIME){ requestKey, bundle ->
            val time = bundle.getLong(CalendarDialog.KEY_DATE_TIME)
            if(time > 0) writeViewModel.setDate(time)
        }
    }
    private fun loadIfData(){
        if(writeViewModel.writeMode == WriteMode.EDIT){
            lifecycleScope.launch {
                val memo = memoViewModel.selectMemo(writeViewModel.id)
                memo?.let {
                    writeViewModel.setMemo(it)
                }
            }
        }
    }
    private fun writeMemo(){
        if(writeViewModel.writeMode == WriteMode.WRITE)
            saveMemo()
        else
            editMemo()
    }
    private fun showCalendar(){
        findNavController().navigate(R.id.action_writeFragment_to_calendarDialog, bundleOf(CalendarDialog.KEY_DATE_TIME to writeViewModel.date.value))
    }
    private fun saveMemo(){
        lifecycleScope.launch {
            val memo = Memo(
                title = binding.etWriteTitle.text.toString(),
                content = binding.etWriteContent.text.toString(),
                regDate = Date().time,
                hasDue = binding.switchUseDue.isChecked,
                dueDate = Date().time,
            )
            MemoRepository.instance.insertMemo(memo)
            exit()
        }
    }
    private fun editMemo(){
        lifecycleScope.launch {
            if(writeViewModel.memo.isInitialized){
                writeViewModel.memo.value?.copy(
                    title = binding.etWriteTitle.text.toString(),
                    content = binding.etWriteContent.text.toString(),
                    hasDue = binding.switchUseDue.isChecked,
                    dueDate = Date().time,
                )?.let {
                    MemoRepository.instance.updateMemo(it)
                    exit()
                }
            }
        }
    }
    private fun exit(){
        parentFragmentManager.popBackStack()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initListener()
    }
    override fun onResume() {
        super.onResume()
        loadIfData()
    }
    companion object {
        @JvmStatic
        fun newInstance() = WriteFragment()
    }
}

enum class WriteMode{
    WRITE, EDIT
}