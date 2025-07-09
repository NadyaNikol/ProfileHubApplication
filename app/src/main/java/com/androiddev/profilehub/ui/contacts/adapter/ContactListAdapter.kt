package com.androiddev.profilehub.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.profilehub.databinding.ListItemContactBinding
import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.util.loadImage
import javax.inject.Inject


/**
 * Created by Nadya N. on 08.05.2025.
 */
class ContactListAdapter @Inject constructor(
    private val onDeleteClick: (itemId: Long) -> Unit,
) :
    ListAdapter<ContactUIEntity, RecyclerView.ViewHolder>(ContactItemsCallback) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactItemHolder(
            binding = ListItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onDeleteClick = onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactItemHolder).bind(currentList[position])
    }

    private class ContactItemHolder(
        private val binding: ListItemContactBinding,
        private val onDeleteClick: (itemId: Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgBtnDeleteIcon.setOnClickListener {
                onDeleteClick.invoke(itemId)
            }
        }

        fun bind(entity: ContactUIEntity) {
            binding.apply {
                tvNameContact.text = entity.name
                tvCareerContact.text = entity.career
                ivContact.loadImage(itemView, entity.image)
            }
        }
    }

    private object ContactItemsCallback : DiffUtil.ItemCallback<ContactUIEntity>() {
        override fun areItemsTheSame(oldItem: ContactUIEntity, newItem: ContactUIEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ContactUIEntity,
            newItem: ContactUIEntity,
        ): Boolean {
            return oldItem == newItem
        }

    }
}