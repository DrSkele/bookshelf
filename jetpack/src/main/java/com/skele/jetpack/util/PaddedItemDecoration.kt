package com.skele.jetpack.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PaddedItemDecoration(private val top : Int, private val bottom : Int, private val left : Int, private val right : Int) : RecyclerView.ItemDecoration() {
    constructor(vertical : Int = 0, horizontal : Int = 0) : this(vertical, vertical, horizontal, horizontal)
    constructor(padding : Int) : this(padding, padding, padding, padding)
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = top
        outRect.bottom = bottom
        outRect.left = left
        outRect.right = right
    }
}