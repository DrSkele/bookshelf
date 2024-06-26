package com.skele.jetpack.main.list

/**
 * Single Abstract Method ClickListener
 * - fun interface can be used as SAM
 */
fun interface OnItemClickListener<T> {
    fun onClick(item : T)
}