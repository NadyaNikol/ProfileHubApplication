package com.androiddev.profilehub.ui.contacts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.useCases.AddContactUseCase
import com.androiddev.profilehub.domain.useCases.CancelContactAddingUseCase
import com.androiddev.profilehub.domain.useCases.ValidationAddContactUseCase
import com.androiddev.profilehub.ui.contacts.AddContactsState
import com.androiddev.profilehub.ui.contacts.errors.ValidationAddContactError
import com.androiddev.profilehub.ui.contacts.events.AddContactFormEvent
import com.androiddev.profilehub.ui.contacts.events.ContactDialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 03.06.2025.
 */

@HiltViewModel
class AddContactDialogViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val cancelContactAddingUseCase: CancelContactAddingUseCase,
    private val validationAddContactUseCase: ValidationAddContactUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddContactsState())
    val uiState = _uiState.asStateFlow()

    fun onUIEvent(event: ContactDialogEvent) {
        when (event) {
            is ContactDialogEvent.Add -> addContact(event.contact)
            is ContactDialogEvent.Cancel -> onCancelAddContact()
        }
    }

    fun onEvent(event: AddContactFormEvent) {
        when (event) {
            is AddContactFormEvent.NameChanged ->
                _uiState.update { it.copy(name = event.name) }

            is AddContactFormEvent.CareerChanged ->
                _uiState.update { it.copy(career = event.career) }
        }
    }

    private fun addContact(contact: ContactUIEntity) {
        if (!validateData()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(submitDataEvent = Unit) }
            addContactUseCase.addContact(contact)
        }
    }

    private fun onCancelAddContact() {
        viewModelScope.launch {
            _uiState.update { it.copy(submitDataEvent = Unit) }
            cancelContactAddingUseCase.cancelAdd()
        }
    }

    private fun validateData(): Boolean {
        val nameError = validationAddContactUseCase.validateName(uiState.value.name)
        val careerError = validationAddContactUseCase.validateCareer(uiState.value.career)

        _uiState.update {
            it.copy(
                nameError = nameError,
                careerError = careerError
            )
        }

        return nameError == ValidationAddContactError.None && careerError == ValidationAddContactError.None
    }

}