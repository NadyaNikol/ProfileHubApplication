package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.error.ValidationAddContactError
import javax.inject.Inject

/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationAddContactUseCaseImpl @Inject constructor(): ValidationAddContactUseCase {
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

    companion object {
        const val MIN_LENGTH_CAREER = 2
        val NAME_REGEX = "^[A-Za-zА-Яа-яЇїІіЄєҐґ'\\- ]+$".toRegex()
    }

}