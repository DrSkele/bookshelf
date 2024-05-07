package com.skele.jetpack.util

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toDateString() : String{
    return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(this)
}

fun Int.dp(context: Context) : Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}