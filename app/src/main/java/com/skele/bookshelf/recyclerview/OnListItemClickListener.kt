package com.skele.bookshelf.recyclerview

/**
 * Listener for click event on viewholder.
 *
 * Single Abstract Method.
 *
 * Kotlin interface supports SAM with `fun interface`.
 */
fun interface OnListItemClickListener<T> {
    /**
     * On click event with an item.
     */
    fun onClick(item : T) : Unit
}