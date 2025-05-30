package com.androiddev.profilehub.ui.contacts.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

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

//        if (position != RecyclerView.NO_POSITION)
//        val position = viewHolder.adapterPosition
//        val contact = getItem(position)
//
//        deleteItem(viewHolder.adapterPosition)
//        onUndoRequest(contact)
//
//        val data = listAdapter.getObject(position)
//        viewModel.deleteContactById(listAdapter.getItemId(viewHolder.adapterPosition))

//        Snackbar.make(binding.root, "Delete", Snackbar.LENGTH_LONG)
//            .setAction("Undo") {
//                viewModel.saveContact(data)
//            }
//            .show()
    }

//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean,
//    ) {
//        val itemView = viewHolder.itemView
//        val background =
//            ContextCompat.getColor(this@ContactsActivity, R.color.colorError).toDrawable()
//        val deleteIcon =
//            ContextCompat.getDrawable(this@ContactsActivity, R.drawable.delete_icon)!!
//
//        background.setBounds(
//            itemView.left, itemView.top,
//            itemView.left + dX.toInt(), itemView.bottom
//        )
//        background.draw(c)
//
//
//        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
//        val iconTop = itemView.top + iconMargin
//        val iconLeft = itemView.left + iconMargin
//        val iconRight = iconLeft + deleteIcon.intrinsicWidth
//        val iconBottom = iconTop + deleteIcon.intrinsicHeight
//
//        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//        deleteIcon.draw(c)
//
//        super.onChildDraw(
//            c,
//            recyclerView,
//            viewHolder,
//            dX,
//            dY,
//            actionState,
//            isCurrentlyActive
//        )
//    }
}