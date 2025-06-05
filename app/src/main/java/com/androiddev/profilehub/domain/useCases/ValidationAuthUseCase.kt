package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.ui.auth.errors.ValidationAuthError

/**
 * Created by Nadya N. on 07.04.2025.
 */
interface ValidationAuthUseCase {
    fun validateEmail(email: String): ValidationAuthError
    fun validatePassword(password: String): ValidationAuthError
}