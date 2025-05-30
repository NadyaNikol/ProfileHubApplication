package com.androiddev.profilehub.ui.contacts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.entities.ContactIndexedUIEntity
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.useCases.AddContactsUseCase
import com.androiddev.profilehub.domain.useCases.DeleteContactsUseCase
import com.androiddev.profilehub.domain.useCases.GetContactsUseCase
import com.androiddev.profilehub.ui.contacts.ContactsState
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent
import com.androiddev.profilehub.ui.contacts.events.UiEvent
import com.androiddev.profilehub.utils.mappers.addByIndex
import com.androiddev.profilehub.utils.mappers.removeAtIndex
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

    private var contactIndexed: ContactIndexedUIEntity? = null

    init {
        getContactsUseCase.contactsFlow.onEach {
            _uiState.update { state ->
                state.copy(
                    items = it.sortedBy { it.name }
                    isLoading = false,
                )
            }
        }.launchIn(viewModelScope)
        loadContacts()
        _uiState.update { state -> state.copy(isLoading = true) }
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
                _uiState.update { it.copy(snackbarEvent = SnackbarEvent.ContactSaved) }
            }

            UiEvent.ContactDialog.Cancel -> {
                _uiState.update { it.copy(snackbarEvent = SnackbarEvent.ContactCancelSaved) }
            }

            is UiEvent.SwipeDelete -> {
                val currentItems = uiState.value.items
                val contactToRemove = currentItems.find { it.id == event.id } ?: return
                val indexToRemove = currentItems.indexOf(contactToRemove)

                contactIndexed = ContactIndexedUIEntity(contact = contactToRemove, index = indexToRemove)

                _uiState.update {
                    it.copy(
                        items = currentItems.removeAtIndex(indexToRemove),
                        snackbarEvent = SnackbarEvent.ContactUndoDeleted
                    )
                }
            }

            is UiEvent.Undo.Clicked -> {
                val currentItems = uiState.value.items
                val contact = contactIndexed?.contact ?: return
                val index = contactIndexed?.index ?: currentItems.size

                _uiState.update { it.copy(items = currentItems.addByIndex(index = index, obj = contact)) }

                contactIndexed = null
            }

            UiEvent.Undo.Dismissed -> {
                val contact = contactIndexed?.contact ?: return
                deleteContactById(contact.id)
            }

            UiEvent.ClearSnackbarMessage -> {
                _uiState.update { it.copy(snackbarEvent = null) }
            }
        }
    }

    private fun deleteContactById(id: Long) {
        viewModelScope.launch {
            deleteContactsUseCase.deleteContactById(id)
        }
    }

    private fun saveContact(contact: ContactUIEntity) {
        viewModelScope.launch {
            addContactsUseCase.saveContact(contact)
        }
    }
}