package com.skele.bookshelf.sqlite

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Task(
    val id: Long,
    var priority: Int,
    var title: String,
    var description: String?,
    val regDate: Long,
    var dueDate: Long?,
    ){

    fun getDueDateFormat() : String = if(dueDate != null) SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(dueDate) else ""

    constructor(
        priority: Int,
        title: String,
        description: String?,
        dueDate: Long? = null
    ) : this(-1, priority, title, description, Calendar.getInstance(Locale.KOREA).timeInMillis, dueDate)
}
