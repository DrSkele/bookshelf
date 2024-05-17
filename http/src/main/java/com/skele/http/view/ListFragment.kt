package com.skele.http.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skele.http.databinding.FragmentListBinding
import com.skele.http.util.hideKeyboard

class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {

    private val viewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    private var page = 1;

    private lateinit var adapter: DocumentAdapter
    private fun initView(){
        adapter = DocumentAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->

            if(event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                fetchData()
                v.hideKeyboard()

                return@setOnEditorActionListener true
            }

            false
        }
    }
    private fun initObserver(){
        viewModel.documentList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
    private fun fetchData(){
        viewModel.loadDocuments(page, binding.etSearch.text.toString())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }
}