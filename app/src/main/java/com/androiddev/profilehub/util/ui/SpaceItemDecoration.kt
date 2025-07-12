package com.androiddev.profilehub.util.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.profilehub.R

/**
 * Created by Nadya N. on 30.06.2025.
 */

class SpaceItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val marginStart: Int =
        context.resources.getDimensionPixelSize(R.dimen.margin_start)
    private val marginEnd: Int =
        context.resources.getDimensionPixelSize(R.dimen.margin_end)
    private val itemMarginTop: Int =
        context.resources.getDimensionPixelSize(R.dimen.item_margin_top)
    private val itemMarginBottom: Int =
        context.resources.getDimensionPixelSize(R.dimen.item_margin_bottom)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        outRect.left = marginStart
        outRect.right = marginEnd
        outRect.top = if (position == 0) itemMarginTop else 0
        outRect.bottom = itemMarginBottom
    }
}