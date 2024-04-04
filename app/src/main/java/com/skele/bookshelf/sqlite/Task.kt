package com.skele.bookshelf.sqlite

import java.util.Date

data class Task(
    val id: Long,
    var priority: Int,
    var title: String,
    var description: String?,
    val regDate: Long,
    var dueDate: Long?,
    )
