package com.example.itunes_search.helper.itemDecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes_search.utils.Utils

class SpaceLeftItemDecoration(context: Context, dp: Int) : RecyclerView.ItemDecoration() {

    private var mSpace = 0

    init {
        mSpace = Utils.dpToPx(context, dp)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = mSpace
    }
}