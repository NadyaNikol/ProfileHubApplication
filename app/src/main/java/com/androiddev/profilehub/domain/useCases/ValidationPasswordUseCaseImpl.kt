package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.ui.auth.errors.AuthError
import com.androiddev.profilehub.utils.MIN_LENGTH_PASSWORD
import javax.inject.Inject


/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationPasswordUseCaseImpl @Inject constructor() : ValidationPasswordUseCase {

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

    override fun validate(password: String): AuthError {
        return when {
            password.isBlank() -> AuthError.Password.EMPTY
            password.length < MIN_LENGTH_PASSWORD -> AuthError.Password.TOO_SHORT
            !containsOnlyEnglishLettersAndDigits(password) -> AuthError.Password.NON_ASCII
            !uppercasePattern.containsMatchIn(password) -> AuthError.Password.NO_UPPERCASE
            !englishLetterPattern.containsMatchIn(password) -> AuthError.Password.NO_LETTER
            !digitPattern.containsMatchIn(password) -> AuthError.Password.NO_DIGIT
            !noSpacePattern.matches(password) -> AuthError.Password.HAS_SPACE

            else -> AuthError.None
        }
    }
}
