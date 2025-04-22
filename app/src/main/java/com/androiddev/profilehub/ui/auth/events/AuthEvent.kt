package com.androiddev.profilehub.ui.auth.events

/**
 * Created by Nadya N. on 18.04.2025.
 */

sealed class AuthEvent {
    data object Success: AuthEvent()

//    data class Error(val errorMessage: String): AuthEvent()
}