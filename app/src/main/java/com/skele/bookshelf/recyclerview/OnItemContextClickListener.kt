package com.skele.bookshelf.recyclerview

import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.View

fun interface OnItemContextClickListener<T> {
    fun onContextClick(menu:ContextMenu, view: View, menuInfo:ContextMenuInfo?, item: T)
}