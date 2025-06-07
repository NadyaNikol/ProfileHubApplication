package com.androiddev.profilehub.ui.contacts.utils

import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.profilehub.R

/**
 * Created by Nadya N. on 23.05.2025.
 */
class ItemTouchHelperImpl(
    private val onDelete: (itemId: Long) -> Unit,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
    }

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int,
    ) {

        onDelete(viewHolder.itemId)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        val itemView = viewHolder.itemView
        val icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.delete_icon) ?: return
        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2

        val iconTop = itemView.top + iconMargin
        val iconBottom = iconTop + icon.intrinsicHeight

        val iconLeft = itemView.left + iconMargin
        val iconRight = iconLeft + icon.intrinsicWidth

        val color = ContextCompat.getColor(recyclerView.context, R.color.colorDeleteBackground)
        val background = color.toDrawable()
        background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        background.draw(c)

        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}