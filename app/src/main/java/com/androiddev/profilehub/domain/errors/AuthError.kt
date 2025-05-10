package com.androiddev.profilehub.domain.errors

/**
 * Created by Nadya N. on 09.05.2025.
 */
sealed interface AuthError {
    object None : AuthError

    enum class Password : AuthError {
        EMPTY,
        TOO_SHORT,
        NON_ASCII,
        NO_UPPERCASE,
        NO_LETTER,
        NO_DIGIT,
        HAS_SPACE
    }

    enum class Email : AuthError {
        EMPTY,
        INVALID
    }
}