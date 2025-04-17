package com.androiddev.profilehub.domain.useCases

import android.util.Patterns

/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationEmailUseCase {

    operator fun invoke(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error(Exception("E-Mail address can't be blank"))
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult.Error(
                Exception("Incorrect E-Mail address")
            )
            else -> ValidationResult.Success("E-Mail address is valid")
        }
    }
}