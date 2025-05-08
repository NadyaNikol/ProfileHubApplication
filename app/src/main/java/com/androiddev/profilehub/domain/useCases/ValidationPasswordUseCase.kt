package com.androiddev.profilehub.domain.useCases

import android.content.res.Resources
import com.androiddev.profilehub.R


/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationPasswordUseCase(private val resources: Resources) {

    private val uppercasePattern =
        Regex(".*[A-Z].*")                // Contains at least one uppercase letter
    private val englishLetterPattern =
        Regex(".*[a-zA-Z].*")         // Contains at least one English letter
    private val digitPattern = Regex(".*\\d.*")                      // Contains at least one digit
    private val noSpacePattern =
        Regex("^\\S*$")                     // No whitespace anywhere in the string

    private fun Char.isAsciiLetterOrDigit(): Boolean {
        return this.isLetterOrDigit() && this.code < 128
    }

    private fun containsOnlyEnglishLettersAndDigits(password: String): Boolean {
        return password.all { it.isAsciiLetterOrDigit() }
    }

    operator fun invoke(password: String): ValidationResult {
        return when {
            password.isBlank() ->
                ValidationResult.Error(resources.getString(R.string.password_must_not_be_blank))

            password.length < 8 ->
                ValidationResult.Error(resources.getString(R.string.password_must_include_a_minimum_characters))

            !containsOnlyEnglishLettersAndDigits(password) ->
                ValidationResult.Error(resources.getString(R.string.password_must_contain_only_english_letters_and_digits))

            !uppercasePattern.containsMatchIn(password) ->
                ValidationResult.Error(resources.getString(R.string.password_must_contain_at_least_one_uppercase_letter))

            !englishLetterPattern.containsMatchIn(password) ->
                ValidationResult.Error(resources.getString(R.string.password_need_to_contain_at_least_one_letter))

            !digitPattern.containsMatchIn(password) ->
                ValidationResult.Error(resources.getString(R.string.password_must_contain_at_least_one_digit))

            !noSpacePattern.matches(password) ->
                ValidationResult.Error(resources.getString(R.string.password_must_not_contain_spaces))

            else -> ValidationResult.Success
        }
    }
}