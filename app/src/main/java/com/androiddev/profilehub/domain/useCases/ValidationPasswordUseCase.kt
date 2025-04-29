package com.androiddev.profilehub.domain.useCases

import android.content.Context
import com.androiddev.profilehub.R


/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationPasswordUseCase(private val context: Context) {

    operator fun invoke(password: String): ValidationResult {
        val containsLetters = password.any { it.isLetter() }
        val containsDigits = password.any { it.isDigit() }
        val containsUppercase = password.any { it.isUpperCase() }

        return when {
            password.length < 8 -> ValidationResult.Error(context.getString(R.string.password_must_include_a_minimum_characters))
            !containsLetters && !containsDigits -> ValidationResult.Error(
                context.getString(R.string.password_need_to_contain_at_least_one_letter_or_digit)
            )

            !containsUppercase ->
                ValidationResult.Error(
                    context.getString(R.string.password_must_contain_at_least_one_uppercase_letter)
                )

            password.contains(" ") -> ValidationResult.Error(
                context.getString(R.string.password_must_not_contain_spaces)
            )

            else -> ValidationResult.Success
        }
    }
}