package com.skele.jetpack.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skele.jetpack.data.Memo
import com.skele.jetpack.databinding.ListItemMemoBinding
import com.skele.jetpack.util.toDateString

/**
 * ### ListAdapter
 * Adapter for recycler view
 * - With DiffUtil, list modification doesn't need to be notified anymore.
 *
 * Recycler view를 위한 Adapter
 * - DiffUtil을 사용함으로서 notify를 수동으로 할 필요가 없다.
 */
class MemoListAdapter : ListAdapter<Memo, MemoListAdapter.MemoViewHolder>(MemoComparator) {
    private var _onItemClickListener : OnItemClickListener<Memo>? = null
    fun setOnListItemClickListener(onItemClickListener: OnItemClickListener<Memo>){
        _onItemClickListener = onItemClickListener
    }

    inner class MemoViewHolder(val binding : ListItemMemoBinding) : ViewHolder(binding.root){
        fun bind(item : Memo){
            binding.apply {
                textListItemMemoTitle.text = item.title
                textListItemMemoRegdate.text = item.regDate.toDateString()
            }
            binding.cardListItem.setOnClickListener {
                _onItemClickListener?.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MemoViewHolder(ListItemMemoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}