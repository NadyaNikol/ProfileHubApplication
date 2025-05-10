package com.androiddev.profilehub.ui.auth.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.data.local.AuthCredentials
import com.androiddev.profilehub.domain.errors.AuthError
import com.androiddev.profilehub.domain.repositories.UserPreferencesRepository
import com.androiddev.profilehub.domain.useCases.ValidationEmailUseCase
import com.androiddev.profilehub.domain.useCases.ValidationPasswordUseCase
import com.androiddev.profilehub.ui.auth.AuthState
import com.androiddev.profilehub.ui.auth.events.AuthFormEvent
import com.androiddev.profilehub.utils.mappers.AuthErrorMessageResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nadya N. on 07.04.2025.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validationEmailUseCase: ValidationEmailUseCase,
    private val validationPasswordUseCase: ValidationPasswordUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState = _uiState.asStateFlow()

    init {
        getCredentials()
    }

    private fun getCredentials() {
        viewModelScope.launch {

            userPreferencesRepository.getSavedCredentials()
                .onSuccess { credentials ->

                    _uiState.update {
                        it.copy(
                            email = credentials.email,
                            password = credentials.password,
                            isRememberMe = credentials.isRememberMe,
                        )
                    }
                }

        }
    }

    fun onEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.EmailChanged ->
                _uiState.update { it.copy(email = event.email) }

            is AuthFormEvent.PasswordChanged ->
                _uiState.update { it.copy(password = event.password) }

            is AuthFormEvent.RememberMeChanged ->
                _uiState.update { it.copy(isRememberMe = event.isChecked) }

            is AuthFormEvent.Submit -> submitData()
        }
    }

    private fun submitData() {
        if (!validateData()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            changeCredentialsByRememberMe()

            _uiState.update { it.copy(isLoading = false, submitDataEvent = Unit) }
        }
    }

    private fun validateData(): Boolean {
        val emailError = validationEmailUseCase.validate(uiState.value.email)
        val passwordError = validationPasswordUseCase.validate(uiState.value.password)

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return emailError == AuthError.None && passwordError == AuthError.None
    }

    private suspend fun changeCredentialsByRememberMe() {
        val state = uiState.value
        if (state.isRememberMe) {
            userPreferencesRepository.saveCredentials(
                AuthCredentials(
                    email = state.email,
                    password = state.password,
                    isRememberMe = state.isRememberMe
                )
            )
        } else {
            userPreferencesRepository.clearCredentials()
        }
    }

}