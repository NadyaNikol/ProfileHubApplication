package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.errors.AuthError


/**
 * Created by Nadya N. on 07.04.2025.
 */
interface ValidationPasswordUseCase {
    fun validate(password: String): AuthError
}