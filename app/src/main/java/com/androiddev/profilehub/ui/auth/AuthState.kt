package com.androiddev.profilehub.ui.auth

/**
 * Created by Nadya N. on 07.04.2025.
 */
data class AuthState(
    val email: String = "",
    val emailError: String? = "",
    val password: String = "",
    val passwordError: String? = "",
    val isLoading: Boolean = false,
)