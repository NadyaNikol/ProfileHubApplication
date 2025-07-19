package com.androiddev.profilehub.ui.contacts.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.FragmentContactsBinding
import com.androiddev.profilehub.ui.BaseFragment
import com.androiddev.profilehub.ui.contacts.ContactsActivity
import com.androiddev.profilehub.ui.contacts.ContactsUIState
import com.androiddev.profilehub.ui.contacts.adapter.ContactListAdapter
import com.androiddev.profilehub.ui.contacts.event.SnackbarEvent
import com.androiddev.profilehub.ui.contacts.event.UiEvent
import com.androiddev.profilehub.ui.contacts.fragment.AddContactDialogFragment.Companion.ADD_CONTACT_DIALOG_TAG
import com.androiddev.profilehub.ui.contacts.listener.ContactClickListener
import com.androiddev.profilehub.ui.contacts.viewModel.ContactViewModel
import com.androiddev.profilehub.util.UIMessageResolver
import com.androiddev.profilehub.util.extension.snackbarBuilder
import com.androiddev.profilehub.util.touch.ContactsItemTouchHelperImpl
import com.androiddev.profilehub.util.ui.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 12.07.2025.
 */

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>(
    FragmentContactsBinding::inflate
) {

    private val viewModel: ContactViewModel by viewModels()
    private lateinit var listAdapter: ContactListAdapter

    @Inject
    lateinit var messageResolver: UIMessageResolver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        initRecyclerView()
        initObserves()
        initListeners()
        initToolbar()
    }

    private fun setupAdapter() {
        listAdapter = ContactListAdapter(object: ContactClickListener {

            override fun onDeleteClick(itemId: Long) {
                viewModel.onUiEvent(UiEvent.SwipeDelete(itemId))
            }

            override fun onItemClick(itemId: Long) {
                val action =
                    ContactsFragmentDirections.actionContactsFragmentToContactsDetailViewFragment(
                        itemId = itemId,
                    )

                findNavController().navigate(action)
            }
        })
    }


    private fun initToolbar() {
        val toolbar = (requireActivity() as ContactsActivity).binding.toolBarContacts

        toolbar.title = getString(R.string.tool_bar_contacts_title)
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_toolbar)
    }

    private fun initListeners() {
        binding.tvAddContacts.setOnClickListener {
            AddContactDialogFragment().show(parentFragmentManager, ADD_CONTACT_DIALOG_TAG)
        }
    }

    private fun initRecyclerView() {
        binding.rvContacts.apply {
            adapter = listAdapter
            addItemDecoration(SpaceItemDecoration(requireActivity()))
        }

        val itemTouchHelper = ContactsItemTouchHelperImpl(requireActivity()) { id ->
            viewModel.onUiEvent(UiEvent.SwipeDelete(id))
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvContacts)
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    renderList(state)
                    renderLoadingState(state)
                    renderSnackbar(state)

                }
            }
        }
    }

    private fun renderList(state: ContactsUIState) {
        listAdapter.submitList(state.items)
    }

    private fun renderLoadingState(state: ContactsUIState) = with(binding) {
        progressBarLoadData.isVisible = state.isLoading
        ivNoData.isVisible = !state.isLoading && state.isNoDataVisible
    }

    private fun renderSnackbar(state: ContactsUIState) {
        state.snackbarEvent?.let { event ->
            val message = messageResolver.resolveSnackbarMessage(event)
            when (event) {
                is SnackbarEvent.Info -> showSnackbar(message)
                is SnackbarEvent.Actionable -> showSnackbar(
                    message = message,
                    textActionResId = event.textActionResId,
                    action = event.onAction
                )
            }
        }
    }

    private fun showSnackbar(message: String) {
        binding.root.snackbarBuilder(message, Snackbar.LENGTH_SHORT)
            .setStyle()
            .setCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    handleSnackbarShown()
                }
            })
            .show()
    }

    private fun showSnackbar(message: String, textActionResId: Int, action: () -> Unit) {
        binding.root.snackbarBuilder(message, Snackbar.LENGTH_LONG)
            .setStyle()
            .setAction(getString(textActionResId)) {
                action()
            }
            .setCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    handleSnackbarShown()
                }
            })
            .show()
    }

    private fun handleSnackbarShown() {
        viewModel.onUiEvent(UiEvent.ClearSnackbarMessage)
    }
}