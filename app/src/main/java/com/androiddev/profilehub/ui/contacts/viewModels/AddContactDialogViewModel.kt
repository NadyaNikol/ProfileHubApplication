package com.androiddev.profilehub.ui.contacts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.useCases.AddContactUseCase
import com.androiddev.profilehub.domain.useCases.CancelContactAddingUseCase
import com.androiddev.profilehub.ui.contacts.events.ContactDialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 03.06.2025.
 */

@HiltViewModel
class AddContactDialogViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val cancelContactAddingUseCase: CancelContactAddingUseCase,
): ViewModel() {

    fun onEvent(event: ContactDialogEvent) {
        when(event){
            is ContactDialogEvent.Save -> addContact(event.contact)
            is ContactDialogEvent.Cancel -> onCancelAddContact()
        }
    }

    private fun addContact(contact: ContactUIEntity){
        viewModelScope.launch {
            addContactUseCase.addContact(contact)
        }
    }

    private fun onCancelAddContact(){
        viewModelScope.launch {
            cancelContactAddingUseCase.cancelAdd()
        }
    }

}