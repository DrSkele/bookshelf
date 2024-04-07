package com.skele.bookshelf.sqlite

import java.text.SimpleDateFormat
import java.util.Locale

data class Task(
    val id: Long,
    var priority: Int,
    var title: String,
    var description: String?,
    val regDate: Long,
    var dueDate: Long?,
    ){

    val dueDateFormat : String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(dueDate)
}
