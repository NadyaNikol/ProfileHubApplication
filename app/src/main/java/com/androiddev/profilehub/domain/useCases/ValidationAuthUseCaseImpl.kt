package com.androiddev.profilehub.domain.useCases

import android.util.Patterns
import com.androiddev.profilehub.ui.auth.errors.ValidationAuthError
import com.androiddev.profilehub.utils.MIN_LENGTH_PASSWORD

/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationAuthUseCaseImpl : ValidationAuthUseCase {

    // Contains at least one uppercase letter
    private val uppercasePattern = Regex(".*[A-Z].*")

    // Contains at least one English letter
    private val englishLetterPattern = Regex(".*[a-zA-Z].*")

    // Contains at least one digit
    private val digitPattern = Regex(".*\\d.*")

    // No whitespace anywhere in the string
    private val noSpacePattern = Regex("^\\S*$")

    private fun Char.isAsciiLetterOrDigit(): Boolean {
        return this in '0'..'9' || this in 'A'..'Z' || this in 'a'..'z'
    }

    private fun containsOnlyEnglishLettersAndDigits(password: String): Boolean {
        return password.all { it.isAsciiLetterOrDigit() }
    }

    override fun validateEmail(email: String): ValidationAuthError {
        return when {
            email.isBlank() -> ValidationAuthError.Email.EMPTY
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationAuthError.Email.INVALID

            else -> ValidationAuthError.None
        }
    }

    override fun validatePassword(password: String): ValidationAuthError {
        return when {
            password.isBlank() -> ValidationAuthError.Password.EMPTY
            password.length < MIN_LENGTH_PASSWORD -> ValidationAuthError.Password.TOO_SHORT
            !containsOnlyEnglishLettersAndDigits(password) -> ValidationAuthError.Password.NON_ASCII
            !uppercasePattern.containsMatchIn(password) -> ValidationAuthError.Password.NO_UPPERCASE
            !englishLetterPattern.containsMatchIn(password) -> ValidationAuthError.Password.NO_LETTER
            !digitPattern.containsMatchIn(password) -> ValidationAuthError.Password.NO_DIGIT
            !noSpacePattern.matches(password) -> ValidationAuthError.Password.HAS_SPACE

            else -> ValidationAuthError.None
        }
    }

}