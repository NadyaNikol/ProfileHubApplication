package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.error.ValidationAddContactError

/**
 * Created by Nadya N. on 07.04.2025.
 */
interface ValidationAddContactUseCase {
    fun validateName(name: String): ValidationAddContactError
    fun validateCareer(career: String): ValidationAddContactError
}