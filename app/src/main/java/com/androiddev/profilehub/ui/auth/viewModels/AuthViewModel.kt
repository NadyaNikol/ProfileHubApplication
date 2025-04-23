package com.androiddev.profilehub.ui.auth.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.data.local.AuthCredentials
import com.androiddev.profilehub.domain.repositories.UserPreferencesRepository
import com.androiddev.profilehub.domain.useCases.ValidationEmailUseCase
import com.androiddev.profilehub.domain.useCases.ValidationPasswordUseCase
import com.androiddev.profilehub.domain.useCases.ValidationResult
import com.androiddev.profilehub.ui.auth.AuthState
import com.androiddev.profilehub.ui.auth.events.AuthEvent
import com.androiddev.profilehub.ui.auth.events.AuthFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    private val _authEvent = MutableSharedFlow<AuthEvent>()
    val authEvent = _authEvent.asSharedFlow()

    init {
        fillCredentials()
    }

    private fun fillCredentials() {
        viewModelScope.launch {
            val credentials = userPreferencesRepository.savedCredentials.first()
            if (credentials != null) {
                _authEvent.emit(
                    AuthEvent.FillSavedCredentials(
                        email = credentials.email,
                        password = credentials.password,
                        rememberMe = credentials.rememberMe
                    )
                )
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
                _uiState.update { it.copy(rememberMe = event.isChecked) }

            is AuthFormEvent.Submit -> submitData()
        }
    }

    private fun submitData() {
        val emailValidationResult = validationEmailUseCase(email = uiState.value.email)
        val passwordValidationResult = validationPasswordUseCase(password = uiState.value.password)

        val emailError = (emailValidationResult as? ValidationResult.Error)?.errorMessage
        val passwordError = (passwordValidationResult as? ValidationResult.Error)?.errorMessage

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        if (emailError != null || passwordError != null) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val state = uiState.value
            if (state.rememberMe) {
                userPreferencesRepository.saveCredentials(
                    AuthCredentials(
                        email = state.email,
                        password = state.password,
                        rememberMe = state.rememberMe
                    )
                )
            } else {
                userPreferencesRepository.clearCredentials()
            }

            delay(3000)

            _uiState.update { it.copy(isLoading = false) }
            _authEvent.emit(AuthEvent.NavigateToMain)
        }
    }

}