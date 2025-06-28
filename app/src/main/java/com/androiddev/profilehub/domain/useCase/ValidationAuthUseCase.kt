package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.error.ValidationAuthError

/**
 * Created by Nadya N. on 07.04.2025.
 */
interface ValidationAuthUseCase {
    fun validateEmail(email: String): ValidationAuthError
    fun validatePassword(password: String): ValidationAuthError
}