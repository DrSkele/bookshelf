package com.skele.http.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.skele.http.data.Document
import com.skele.http.databinding.ListItemBinding

class DocumentAdapter : ListAdapter<Document, DocumentAdapter.DocumentViewHolder>(DocumentDiffer) {

    inner class DocumentViewHolder(val binding : ListItemBinding) : ViewHolder(binding.root){
        fun bind(item : Document){
            binding.tvListItemTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DocumentViewHolder(ListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object DocumentDiffer : DiffUtil.ItemCallback<Document>(){
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem.isbn == newItem.isbn
    }

    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }

}