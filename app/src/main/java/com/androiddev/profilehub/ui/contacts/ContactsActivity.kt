package com.androiddev.profilehub.ui.contacts

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.androiddev.profilehub.R
import com.androiddev.profilehub.databinding.ActivityContactsBinding
import com.androiddev.profilehub.domain.entities.ContactIndexed
import com.androiddev.profilehub.domain.messages.SnackbarMessage
import com.androiddev.profilehub.ui.BaseActivity
import com.androiddev.profilehub.ui.contacts.AddContactDialogFragment.Companion.ADD_CONTACT_DIALOG_TAG
import com.androiddev.profilehub.ui.contacts.adapters.ContactListAdapter
import com.androiddev.profilehub.ui.contacts.events.UiEvent
import com.androiddev.profilehub.ui.contacts.utils.ItemTouchHelperImpl
import com.androiddev.profilehub.ui.contacts.viewModels.ContactViewModel
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

    private val viewModel by viewModels<ContactViewModel>()
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
                        progressBarLoadData.isGone = !state.isLoading
                        ivNoData.isGone = state.isLoading || !state.isEmptyListVisible
                    }

                    state.snackbarMessage?.let { message ->
                        when (message) {
                            is SnackbarMessage.ContactSaved,
                            is SnackbarMessage.ContactCancelSaved,
                                -> {
                                showInfoSnackbar(messageResolver.resolveSnackbarMessage(message))
                            }

                            is SnackbarMessage.ContactUndoDeleted -> {
                                showUndoSnackbar(messageResolver.resolveSnackbarMessage(message))
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

    private fun showUndoSnackbar(message: String) {
        binding.root.snackbarBuilder(message, Snackbar.LENGTH_LONG)
            .setStyle()
            .setAction(getString(R.string.contact_undo)) {
                viewModel.onUiEvent(UiEvent.Undo.Clicked)
            }
            .setCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    handleSnackbarShown()
                }

                override fun onDismissed(
                    transientBottomBar: Snackbar?,
                    event: Int,
                ) {
                    super.onDismissed(transientBottomBar, event)
                    if (event != DISMISS_EVENT_ACTION) {
                        viewModel.onUiEvent(UiEvent.Undo.Dismissed)
                    }
                }
            })
            .show()
    }

    private fun handleSnackbarShown() {
        viewModel.onUiEvent(UiEvent.ClearSnackbarMessage)
    }



    private fun showSnackbar(context: Context, view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)

            .setBackgroundTint(
                ContextCompat.getColor(context, R.color.colorSecondary)
            )
            .setTextColor(
                ContextCompat.getColor(context, R.color.colorOnSecondary)
            )
            .apply {
                view.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.snack_bar_shape_round
                )
            }.show()
    }
}