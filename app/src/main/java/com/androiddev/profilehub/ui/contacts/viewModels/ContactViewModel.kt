package com.androiddev.profilehub.ui.contacts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.useCases.DeleteContactUseCase
import com.androiddev.profilehub.domain.useCases.GetContactsUseCase
import com.androiddev.profilehub.domain.useCases.ObserveContactsEventsUseCase
import com.androiddev.profilehub.domain.useCases.UndoDeleteContactUseCase
import com.androiddev.profilehub.ui.contacts.ContactsState
import com.androiddev.profilehub.ui.contacts.LoadingState
import com.androiddev.profilehub.ui.contacts.events.ContactsEvent
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent.Actionable.ContactUndoDeleted
import com.androiddev.profilehub.ui.contacts.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 08.05.2025.
 */
@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val undoDeleteContactUseCase: UndoDeleteContactUseCase,
    private val observeContactsEventsUseCase: ObserveContactsEventsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsState())
    val uiState = _uiState.asStateFlow()

    init {
        observeEvents()
        observeContacts()
        loadContacts()
    }

    fun loadContacts() {
        viewModelScope.launch {
            getContactsUseCase.loadContacts()
        }
    }

    private fun observeContacts() {
        getContactsUseCase.contactsFlow.onEach { contacts ->
            _uiState.update { state ->
                state.copy(
                    items = contacts.sortedBy { it.name },
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observeEvents() {
        viewModelScope.launch {
            observeContactsEventsUseCase.eventsFlow.onEach { event ->
                when (event) {
                    ContactsEvent.Default -> {
                        _uiState.update { it.copy(loadingState = LoadingState.LoadingInitial) }
                    }

                    is ContactsEvent.Loaded -> {
                        _uiState.update { it.copy(loadingState = LoadingState.Loaded) }
                    }

                    is ContactsEvent.ContactAdded -> {
                        _uiState.update { it.copy(snackbarEvent = SnackbarEvent.Info.ContactAdded) }
                    }

                    is ContactsEvent.ContactDeleted -> {
                        _uiState.update {
                            it.copy(
                                snackbarEvent = ContactUndoDeleted(
                                    onAction = { onUiEvent(UiEvent.Undo.Clicked) }
                                ))
                        }
                    }

                    is ContactsEvent.ContactDeleteUndone ->
                        _uiState.update { it.copy(snackbarEvent = SnackbarEvent.Info.ContactNotDeleted) }

                    ContactsEvent.ContactCancelAdd ->
                        _uiState.update { it.copy(snackbarEvent = SnackbarEvent.Info.ContactCancelAdd) }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ContactDialog.Save -> {
                addContact(event.contact)
                _uiState.update { it.copy(snackbarEvent = SnackbarEvent.Info.ContactAdded) }
            }

            UiEvent.ContactDialog.Cancel -> {
                _uiState.update { it.copy(snackbarEvent = SnackbarEvent.Info.ContactCancelSaved) }
            }

            is UiEvent.SwipeDelete -> deleteContactById(event.id)
            is UiEvent.Undo.Clicked -> undoDelete()

            UiEvent.ClearSnackbarMessage -> {
                _uiState.update { it.copy(snackbarEvent = null) }
            }
        }
    }

    private fun deleteContactById(id: Long) {
        viewModelScope.launch {
            deleteContactUseCase.deleteContactById(id)
        }
    }

    private fun undoDelete() {
        viewModelScope.launch {
            undoDeleteContactUseCase.undoDelete()
        }
    }

    private fun addContact(contact: ContactUIEntity) {
        viewModelScope.launch {
            addContactsUseCase.addContact(contact)
        }
    }
}