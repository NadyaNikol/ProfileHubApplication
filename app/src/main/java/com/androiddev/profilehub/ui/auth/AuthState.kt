package com.androiddev.profilehub.ui.auth

import com.androiddev.profilehub.domain.errors.AuthError

/**
 * Created by Nadya N. on 07.04.2025.
 */
data class AuthState(
    val email: String = "",
    val emailError: AuthError = AuthError.None,
    val password: String = "",
    val passwordError: AuthError = AuthError.None,
    val isLoading: Boolean = false,
    val isRememberMe: Boolean = false,

    val submitDataEvent: Unit? = null,
)