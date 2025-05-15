package com.androiddev.profilehub.ui.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androiddev.profilehub.databinding.ListItemContactBinding
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.events.ViewHolderClickListener
import com.androiddev.profilehub.utils.mappers.loadImage
import javax.inject.Inject


/**
 * Created by Nadya N. on 08.05.2025.
 */
class ContactListAdapter @Inject constructor(
    var callback: ViewHolderClickListener? = null
) : ListAdapter<ContactUIEntity, RecyclerView.ViewHolder>(ContactItemsCallback) {

    fun getObject(position: Int): ContactUIEntity {
        return currentList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactItemHolder(
            binding = ListItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            callback = callback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactItemHolder).bind(currentList[position])
    }

    private class ContactItemHolder(
        private val binding: ListItemContactBinding,
        val callback: ViewHolderClickListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgBtnDeleteIcon.setOnClickListener {
                callback?.onClick(adapterPosition)
            }
        }

        fun bind(entity: ContactUIEntity) {
            binding.apply {
                tvNameContact.text = entity.name
                tvCareerContact.text = entity.career
                imageLoader.load(itemView, entity.image, ivContact)
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