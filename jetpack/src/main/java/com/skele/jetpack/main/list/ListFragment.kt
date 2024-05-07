package com.skele.jetpack.main.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.skele.jetpack.main.MemoViewModel
import com.skele.jetpack.R
import com.skele.jetpack.data.Memo
import com.skele.jetpack.data.MemoRepository
import com.skele.jetpack.databinding.FragmentListBinding
import com.skele.jetpack.util.BaseFragment
import com.skele.jetpack.util.PaddedItemDecoration
import com.skele.jetpack.util.dp

private const val TAG = "ListFragment"
class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {
    private val viewModel : MemoViewModel by activityViewModels()
    private lateinit var adapter : MemoListAdapter
    private fun openWritingPage(){
        findNavController().navigate(R.id.action_listFragment_to_writeFragment)
    }
    private fun openEditingPage(item : Memo){
        val bundle = bundleOf(Memo::id.name to item.id)
        findNavController().navigate(R.id.action_listFragment_to_writeFragment, bundle)
    }
    private fun initView(){
        adapter = MemoListAdapter()
        adapter.setOnListItemClickListener{item ->
            openEditingPage(item)
        }
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.addItemDecoration(PaddedItemDecoration(vertical = 8.dp(requireContext())))
        MemoRepository.instance.getAllMemos().observe(viewLifecycleOwner){list ->
            adapter.submitList(list)
        }

        binding.fabWrite.setOnClickListener{
            openWritingPage()
        }
    }

    /**
     * LiveData Observer
     * - Avoid redundant calls to prevent multiple observers on same data.
     * - Observer is also removed when its lifecycleowner is destroyed.
     * - If observing LiveData has been set to any value, it is passed to the observer on #observe() even the data has not been changed.
     *
     * - 같은 관찰자를 만들지 않기 위해 중복 호출을 피해야한다.
     * - 관찰자는 lifecycleowner가 파괴될 때 같이 사라진다.
     * - LivData가 값을 가지고 있다면, 값이 변경된 적이 없더라도 #observe()로 관찰자를 설정할 때 값이 전달되어 관찰자가 호출된다.
     */
    private fun initObserver(){
        viewModel.memoList.observe(viewLifecycleOwner){list ->
            adapter.submitList(list)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ListFragment()
    }
}