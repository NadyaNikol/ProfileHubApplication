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
import com.androiddev.profilehub.ui.contacts.adapter.ContactListAdapter
import com.androiddev.profilehub.ui.contacts.event.SnackbarEvent
import com.androiddev.profilehub.ui.contacts.event.UiEvent
import com.androiddev.profilehub.ui.contacts.fragment.AddContactDialogFragment
import com.androiddev.profilehub.ui.contacts.fragment.AddContactDialogFragment.Companion.ADD_CONTACT_DIALOG_TAG
import com.androiddev.profilehub.util.ContactsItemTouchHelperImpl
import com.androiddev.profilehub.ui.contacts.viewModel.ContactViewModel
import com.androiddev.profilehub.util.UIMessageResolver
import com.androiddev.profilehub.util.SpaceItemDecoration
import com.androiddev.profilehub.util.snackbarBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 08.05.2025.
 */

@AndroidEntryPoint
class ContactsActivity : BaseActivity<ActivityContactsBinding>(ActivityContactsBinding::inflate) {

    private val viewModel: ContactViewModel by viewModels()
    private lateinit var listAdapter: ContactListAdapter

    @Inject
    lateinit var messageResolver: UIMessageResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.rvContacts.apply {
            adapter = listAdapter
            addItemDecoration(SpaceItemDecoration(this@ContactsActivity))
        }

        val itemTouchHelper = ContactsItemTouchHelperImpl(this@ContactsActivity) { id ->
            viewModel.onUiEvent(UiEvent.SwipeDelete(id))
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvContacts)
    }

    private fun initObserves() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach { state ->

                    renderList(state)
                    renderLoadingState(state)
                    renderSnackbar(state)

                }.launchIn(this)
            }
        }
    }

    private fun renderList(state: ContactsState) {
        listAdapter.submitList(state.items)
    }

    private fun renderLoadingState(state: ContactsState) = with(binding) {
        progressBarLoadData.isVisible = state.loadingState is LoadingState.LoadingInitial
        ivNoData.isVisible = state.loadingState == LoadingState.Loaded && state.isNoDataVisible
    }

    private fun renderSnackbar(state: ContactsState) {
        val event = state.snackbarEvent ?: return
        val message = messageResolver.resolveSnackbarMessage(event)
        when (event) {
            is SnackbarEvent.Info -> showInfoSnackbar(message)
            is SnackbarEvent.Actionable -> showActionSnackbar(
                message = message,
                textActionResId = event.textActionResId,
                action = event.onAction
            )
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