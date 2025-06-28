package com.androiddev.profilehub.domain.error

/**
 * Created by Nadya N. on 03.06.2025.
 */
sealed interface ValidationAddContactError {
    data object None : ValidationAddContactError

    enum class Name : ValidationAddContactError {
        EMPTY,
        INVALID
    }

    enum class Career : ValidationAddContactError {
        EMPTY,
        TOO_SHORT
    }
}