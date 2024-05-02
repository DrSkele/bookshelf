package com.skele.jetpack.main.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.skele.jetpack.data.Memo
import com.skele.jetpack.databinding.FragmentWriteBinding
import com.skele.jetpack.main.MemoViewModel
import com.skele.jetpack.util.BaseFragment


class WriteFragment : BaseFragment<FragmentWriteBinding>(FragmentWriteBinding::inflate) {
    private val viewModel : MemoViewModel by activityViewModels()
    private var id : Long? = null
    private fun initData(){
        id = arguments?.getLong(Memo::id.name)
    }
    private fun initView(item : Memo? = null){

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = WriteFragment()
    }
}