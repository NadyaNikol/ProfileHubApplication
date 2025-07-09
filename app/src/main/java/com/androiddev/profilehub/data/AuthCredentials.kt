package com.androiddev.profilehub.data

/**
 * Created by Nadya N. on 23.04.2025.
 */
data class AuthCredentials(
    val email: String = "",
    val password: String = "",
    val isRememberMe: Boolean = false,
)