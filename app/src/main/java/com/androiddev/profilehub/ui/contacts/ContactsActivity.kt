package com.androiddev.profilehub.ui.contacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.androiddev.profilehub.databinding.ActivityContactsBinding
import com.androiddev.profilehub.ui.BaseActivity
import com.androiddev.profilehub.ui.contacts.adapters.ContactListAdapter
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent
import com.androiddev.profilehub.ui.contacts.events.UiEvent
import com.androiddev.profilehub.ui.contacts.utils.ItemTouchHelperImpl
import com.androiddev.profilehub.ui.contacts.viewModels.ContactViewModel
import com.androiddev.profilehub.utils.ADD_CONTACT_DIALOG_TAG
import com.androiddev.profilehub.utils.UIMessageResolver
import com.androiddev.profilehub.utils.snackbarBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Nadya N. on 08.05.2025.
 */

@AndroidEntryPoint
class ContactsActivity : BaseActivity<ActivityContactsBinding>(ActivityContactsBinding::inflate) {

    private val viewModel: ContactViewModel by viewModels()
    private lateinit var listAdapter: ContactListAdapter
    private lateinit var messageResolver: UIMessageResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messageResolver = UIMessageResolver(this)
        listAdapter = ContactListAdapter()

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

        val itemTouchHelper = ItemTouchHelperImpl { id ->
            viewModel.onUiEvent(UiEvent.SwipeDelete(id))
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvContacts)
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach { state ->
                    listAdapter.submitList(state.items)

                    binding.apply {
                        progressBarLoadData.isVisible = state.loadingState is LoadingState.LoadingInitial
                        ivNoData.isVisible = state.loadingState == LoadingState.Loaded && state.isNoDataVisible
                    }

                    state.snackbarEvent?.let { event ->
                        val message = messageResolver.resolveSnackbarMessage(event)
                        when (event) {
                            is SnackbarEvent.Info -> {
                                showInfoSnackbar(message)
                            }

                            is SnackbarEvent.Actionable -> {
                                showActionSnackbar(
                                    message = message,
                                    textActionResId = event.textActionResId,
                                    action = event.onAction,)
                            }
                        }
                    }

                }.launchIn(this)
            }
        }
    }

    private fun showInfoSnackbar(message: String) {
        binding.root.snackbarBuilder(message, Snackbar.LENGTH_LONG)
            .setStyle()
            .setCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    handleSnackbarShown()
                }
            })
            .show()
    }

    private fun showActionSnackbar(message: String, textActionResId: Int, action: () -> Unit) {
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