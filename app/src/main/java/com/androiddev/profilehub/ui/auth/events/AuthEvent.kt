package com.androiddev.profilehub.ui.auth.events

/**
 * Created by Nadya N. on 18.04.2025.
 */

sealed class AuthEvent {
    data object NavigateToMain : AuthEvent()

    data class FillSavedCredentials(
        val email: String,
        val password: String,
        val rememberMe: Boolean,
    ) : AuthEvent()

//    data class Error(val errorMessage: String): AuthEvent()
}