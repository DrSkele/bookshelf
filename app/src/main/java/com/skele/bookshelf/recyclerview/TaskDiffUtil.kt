package com.skele.bookshelf.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.skele.bookshelf.sqlite.Task

/**
 * DiffUtil for TaskAdapter.
  */
class TaskDiffUtil : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return (oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.dueDate == newItem.dueDate)
    }

}