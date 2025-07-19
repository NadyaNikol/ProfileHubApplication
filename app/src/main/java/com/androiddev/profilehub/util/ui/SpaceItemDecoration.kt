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

    private val itemMarginBottom: Int =
        context.resources.getDimensionPixelSize(R.dimen.item_margin_bottom)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        if (position == RecyclerView.NO_POSITION || position == itemCount - 1) return

        outRect.bottom = itemMarginBottom
    }
}