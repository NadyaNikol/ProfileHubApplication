package com.androiddev.profilehub.ui.auth.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.profilehub.domain.useCases.ValidationEmailUseCase
import com.androiddev.profilehub.domain.useCases.ValidationPasswordUseCase
import com.androiddev.profilehub.domain.useCases.ValidationResult
import com.androiddev.profilehub.ui.auth.AuthFormEvent
import com.androiddev.profilehub.ui.auth.AuthState
import com.androiddev.profilehub.ui.auth.events.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState = _uiState.asStateFlow()

    private val _authEvent = MutableSharedFlow<AuthEvent>()
    val authEvent =_authEvent.asSharedFlow()

    fun onEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.EmailChanged ->
                _uiState.update { it.copy(email = event.email) }
            is AuthFormEvent.PasswordChanged ->
                _uiState.update { it.copy(password = event.password) }
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

            delay(3000)

            _uiState.update { it.copy(isLoading = false) }
            _authEvent.emit(AuthEvent.Success)
        }
    }

}