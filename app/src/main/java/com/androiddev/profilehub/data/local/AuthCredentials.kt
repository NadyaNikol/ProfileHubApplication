package com.androiddev.profilehub.data.local

/**
 * Created by Nadya N. on 23.04.2025.
 */
data class AuthCredentials(
    val email: String,
    val password: String,
    val rememberMe: Boolean,
)