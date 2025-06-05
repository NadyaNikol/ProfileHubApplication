package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.ui.contacts.errors.ValidationAddContactError
import com.androiddev.profilehub.utils.MIN_LENGTH_CAREER
import com.androiddev.profilehub.utils.NAME_REGEX

/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationAddContactUseCaseImpl : ValidationAddContactUseCase {
    override fun validateName(name: String): ValidationAddContactError {
        return when {
            name.isBlank() -> ValidationAddContactError.Name.EMPTY
            !name.matches(NAME_REGEX) -> ValidationAddContactError.Name.INVALID

            else -> ValidationAddContactError.None
        }
    }

    override fun validateCareer(career: String): ValidationAddContactError {
        return when {
            career.isBlank() -> ValidationAddContactError.Career.EMPTY
            career.length < MIN_LENGTH_CAREER -> ValidationAddContactError.Career.TOO_SHORT

            else -> ValidationAddContactError.None
        }
    }

}