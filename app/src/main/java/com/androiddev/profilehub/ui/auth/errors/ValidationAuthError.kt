package com.androiddev.profilehub.ui.auth.errors

/**
 * Created by Nadya N. on 09.05.2025.
 */
sealed interface ValidationAuthError {
    object None : ValidationAuthError

    enum class Password : ValidationAuthError {
        EMPTY,
        TOO_SHORT,
        NON_ASCII,
        NO_UPPERCASE,
        NO_LETTER,
        NO_DIGIT,
        HAS_SPACE
    }

    enum class Email : ValidationAuthError {
        EMPTY,
        INVALID
    }
}