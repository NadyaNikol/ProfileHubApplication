package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.ui.contacts.errors.ValidationAddContactError

/**
 * Created by Nadya N. on 07.04.2025.
 */
interface ValidationAddContactUseCase {
    fun validateName(name: String): ValidationAddContactError
    fun validateCareer(career: String): ValidationAddContactError
}