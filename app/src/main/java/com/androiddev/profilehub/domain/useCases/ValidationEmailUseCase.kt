package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.ui.auth.errors.AuthError

/**
 * Created by Nadya N. on 07.04.2025.
 */
interface ValidationEmailUseCase {
    fun validate(email: String): AuthError
}