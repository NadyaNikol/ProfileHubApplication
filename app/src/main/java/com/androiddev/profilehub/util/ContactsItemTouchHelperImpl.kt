package com.androiddev.profilehub.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.profilehub.R

/**
 * Created by Nadya N. on 23.05.2025.
 */
class ContactsItemTouchHelperImpl(
    private val context: Context,
    private val onDelete: (itemId: Long) -> Unit,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val rect = RectF()

    private val cornerRadius =
        context.resources.getDimension(R.dimen.material_card_view_corner_radius)
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorDeleteBackground)
        isAntiAlias = true
    }

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

        with(viewHolder.itemView) {
            rect.set(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
            )
        }

        c.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}