package com.androiddev.profilehub.ui.contacts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.useCases.AddContactsUseCase
import com.androiddev.profilehub.domain.useCases.DeleteContactsUseCase
import com.androiddev.profilehub.domain.useCases.GetContactsUseCase
import com.androiddev.profilehub.ui.contacts.ContactsState
import com.androiddev.profilehub.ui.contacts.events.ContactEvent
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
    private val addContactsUseCase: AddContactsUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsState())
    val uiState = _uiState.asStateFlow()

    val contactsFlow = getContactsUseCase.contactsFlow.onEach {
        _uiState.update { state ->
            state.copy(
                items = it.sortedBy { it.name }
            )
        }
    }

    init {
        contactsFlow.launchIn(viewModelScope)
        loadContacts()
    }

    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.SaveContact -> {
                saveContact(event.contact)
            }

            is ContactEvent.CancelSaveContact -> TODO()
        }
    }

    fun loadContacts() {
        viewModelScope.launch {
            getContactsUseCase.loadContacts()
        }
    }

    private var contactIndexed: ContactIndexed? = null

    fun onUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ContactDialog.Save -> {
                saveContact(event.contact)
                _uiState.update { it.copy(snackbarMessage = SnackbarMessage.ContactSaved) }
            }

            UiEvent.ContactDialog.Cancel -> {
                _uiState.update { it.copy(snackbarMessage = SnackbarMessage.ContactCancelSaved) }
            }

            is UiEvent.SwipeDelete -> {
                val currentItems = uiState.value.items
                val contactToRemove = currentItems.find { it.id == event.id } ?: return
                val indexToRemove = currentItems.indexOf(contactToRemove)

                contactIndexed = ContactIndexed(contact = contactToRemove, index = indexToRemove)

                _uiState.update {
                    it.copy(
                        items = currentItems.removeAtIndex(indexToRemove),
                        snackbarMessage = SnackbarMessage.ContactUndoDeleted
                    )
                }
            }

            UiEvent.Undo.Clicked -> {
                val currentItems = uiState.value.items
                val contact = contactIndexed?.contact ?: return
                val index = contactIndexed?.index ?: currentItems.size

                _uiState.update { it.copy(items = currentItems.addByIndex(index = index, obj = contact)) }

                contactIndexed = null
            }

    fun saveContact(contact: ContactUIEntity) {
        viewModelScope.launch {
            addContactsUseCase.saveContact(contact)
        }
    }

}