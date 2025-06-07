package com.androiddev.profilehub.ui.contacts.errors

/**
 * Created by Nadya N. on 03.06.2025.
 */
sealed interface ValidationAddContactError {
    object None : ValidationAddContactError

    enum class Name : ValidationAddContactError {
        EMPTY,
        INVALID
    }

    enum class Career : ValidationAddContactError {
        EMPTY,
        TOO_SHORT
    }
}