package com.skele.bookshelf.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skele.bookshelf.databinding.ListItemTaskBinding
import com.skele.bookshelf.sqlite.Task


class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffUtil()){
    private var onListItemClickListener: OnListItemClickListener<Task>? = null
    private var onItemContextClickListener: OnItemContextClickListener<Task>? = null

    /**
     * Register a callback to be invoked when list item is clicked.
     */
    fun setOnListItemClickListener(onListItemClickListener: OnListItemClickListener<Task>){
        this.onListItemClickListener = onListItemClickListener
    }
    fun setOnItemContextClickListener(onItemContextClickListener: OnItemContextClickListener<Task>){
        this.onItemContextClickListener = onItemContextClickListener
    }

    // Reusable container of the view displaying the item.
    // ViewHolder only caches its view which binds to different item when `onBindViewHolder` is called.
    class TaskViewHolder(private val viewbinding : ListItemTaskBinding) : RecyclerView.ViewHolder(viewbinding.root){
        fun bind(item : Task, onListItemClickListener: OnListItemClickListener<Task>?, onItemContextClickListener: OnItemContextClickListener<Task>?){
            Log.d("TAG_BIND", "bind: $item")

            viewbinding.taskTitleTextView.text = item.title
            viewbinding.taskDescriptionTextView.text = item.description
            viewbinding.taskDueTextView.text = item.getDueDateFormat()
            viewbinding.cardView.setOnClickListener{
                onListItemClickListener?.onClick(item)
            }
            viewbinding.cardView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                Log.d("TAG", "bind: context menu open $menu, $v, $menuInfo $item")
                onItemContextClickListener?.onContextClick(menu, v, menuInfo, item)
            }
        }
    }

    // Purpose : To create a ViewHolder to show the data
    // How : Inflate a new XML file or create a View
    // Caution : A ViewHolder created here will be re-used to display different items.
    //           Since content of the ViewHolder changes from time to time,
    //           it's best to cache the reference to the view used in the ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return currentList.size
    }
    // Purpose : To display the ViewHolder with the data at the position
    // How : The item fetched from the position binds to the ViewHolder
    // Caution : Unlike ListView, RecyclerView only updates when the item is invalidated or new position cannot be determined.
    //           Because of this, the position parameter cannot be kept to track the item's position.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: ${getItem(position)}")
        holder.bind(getItem(position), onListItemClickListener, onItemContextClickListener)
    }
}