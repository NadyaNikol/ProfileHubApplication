package com.androiddev.profilehub.ui.main.contacts.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.useCase.GetContactUseCase
import com.androiddev.profilehub.ui.main.contacts.ContactDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.07.2025.
 */

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val getContactUseCase: GetContactUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactDetailUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContact()
    }

    private fun loadContact() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            val id: Long? = savedStateHandle.get<Long>("itemId")
            val contact = id?.let { getContactUseCase(it) }

            _uiState.update { state ->
                state.copy(isLoading = false, contact = contact)
            }
        }
    }
}