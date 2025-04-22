package com.androiddev.profilehub.domain.useCases


/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationPasswordUseCase {

    operator fun invoke(password: String): ValidationResult {
        val containsLetters = password.any { it.isLetter() }
        val containsDigits = password.any { it.isDigit() }
        val containsUppercase = password.any { it.isUpperCase() }

        return when {
            password.length < 8 -> ValidationResult.Error("Your password must include a minimum of 8 characters.")
            !containsLetters && !containsDigits -> ValidationResult.Error(
                "Your password need to contain at least one letter or digit"
            )

            !containsUppercase ->
                ValidationResult.Error(
                    "Your password must contain at least one uppercase letter"
                )

            password.contains(" ") -> ValidationResult.Error(
                "Your password must not contain spaces"
            )

            else -> ValidationResult.Success
        }
    }
}