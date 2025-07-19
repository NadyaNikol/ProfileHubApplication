package com.androiddev.profilehub.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.profilehub.databinding.ListItemContactBinding
import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.listener.ContactClickListener
import com.androiddev.profilehub.util.extension.loadImage
import javax.inject.Inject


/**
 * Created by Nadya N. on 08.05.2025.
 */
class ContactListAdapter @Inject constructor(
    private val listener: ContactClickListener,
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
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactItemHolder).bind(currentList[position])
    }

    private class ContactItemHolder(
        private val binding: ListItemContactBinding,
        private val listener: ContactClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgBtnDeleteIcon.setOnClickListener {
                listener.onDeleteClick(itemId)
            }
            binding.root.setOnClickListener {
                listener.onItemClick(itemId)
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