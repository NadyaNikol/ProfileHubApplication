package com.androiddev.profilehub.ui.auth

/**
 * Created by Nadya N. on 07.04.2025.
 */
sealed class AuthFormEvent {
    data class EmailChanged(val email: String): AuthFormEvent()
    data class PasswordChanged(val password: String): AuthFormEvent()

    data object Submit:AuthFormEvent()
}