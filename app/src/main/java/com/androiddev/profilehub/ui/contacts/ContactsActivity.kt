package com.androiddev.profilehub.ui.contacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androiddev.profilehub.databinding.ActivityContactsBinding
import com.androiddev.profilehub.ui.BaseActivity
import com.androiddev.profilehub.ui.contacts.AddContactDialogFragment.Companion.ADD_CONTACT_DIALOG_TAG
import com.androiddev.profilehub.ui.contacts.adapters.ContactListAdapter
import com.androiddev.profilehub.ui.contacts.events.ViewHolderClickListener
import com.androiddev.profilehub.ui.contacts.viewModels.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Nadya N. on 08.05.2025.
 */

@AndroidEntryPoint
class ContactsActivity : BaseActivity<ActivityContactsBinding>(ActivityContactsBinding::inflate) {

    private val viewModel by viewModels<ContactViewModel>()
    private lateinit var listAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter = ContactListAdapter(object : ViewHolderClickListener {
            override fun onClick(position: Int) {
                val data = listAdapter.getObject(position)
                viewModel.deleteContactById(data.id)
            }
        })

        initRecyclerView()
        initObserves()
        initListeners()
    }

    private fun initListeners() {
        binding.tvAddContacts.setOnClickListener {
            AddContactDialogFragment().show(supportFragmentManager, ADD_CONTACT_DIALOG_TAG)
        }
    }

    private fun initRecyclerView() {
        binding.rvContacts.adapter = listAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach {
                    listAdapter.submitList(it.items)
                }.launchIn(this)
            }
        }
    }
}