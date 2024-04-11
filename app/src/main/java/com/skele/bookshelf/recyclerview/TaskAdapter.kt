package com.skele.bookshelf.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skele.bookshelf.databinding.ListItemTaskBinding
import com.skele.bookshelf.sqlite.Task


class TaskAdapter(var list : List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    // Reusable container of the view displaying the item.
    // ViewHolder only caches its view which binds to different item when `onBindViewHolder` is called.
    class TaskViewHolder(private val viewbinding : ListItemTaskBinding) : RecyclerView.ViewHolder(viewbinding.root){
        fun bind(item : Task){
            viewbinding.taskTitleTextView.text = item.title
            viewbinding.taskDescriptionTextView.text = item.description
            viewbinding.taskDueTextView.text = item.dueDateFormat
        }

        companion object{
            fun create(parent: ViewGroup) : TaskViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemTaskBinding.inflate(inflater, parent, false)
                return TaskViewHolder(binding)
            }
        }
    }

    // Purpose : To create a ViewHolder to show the data
    // How : Inflate a new XML file or create a View
    // Caution : A ViewHolder created here will be re-used to display different items.
    //           Since content of the ViewHolder changes from time to time,
    //           it's best to cache the reference to the view used in the ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    // Purpose : To display the ViewHolder with the data at the position
    // How : The item fetched from the position binds to the ViewHolder
    // Caution : Unlike ListView, RecyclerView only updates when the item is invalidated or new position cannot be determined.
    //           Because of this, the position parameter cannot be kept to track the item's position.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }
}