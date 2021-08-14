package com.example.itunes_search.utils

import android.content.Context
import android.util.TypedValue

object Utils {
    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int = if (dp > 0) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt() else dp
}