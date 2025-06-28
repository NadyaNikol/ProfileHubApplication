package com.androiddev.profilehub.ui.auth

import com.androiddev.profilehub.domain.error.ValidationAuthError

/**
 * Created by Nadya N. on 07.04.2025.
 */
data class AuthState(
    val email: String = "",
    val emailError: ValidationAuthError = ValidationAuthError.None,
    val password: String = "",
    val passwordError: ValidationAuthError = ValidationAuthError.None,
    val isLoading: Boolean = false,
    val isRememberMe: Boolean = false,

    val submitDataEvent: Unit? = null,
)