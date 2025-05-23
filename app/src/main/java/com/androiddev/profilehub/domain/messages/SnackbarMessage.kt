package com.androiddev.profilehub.domain.messages

/**
 * Created by Nadya N. on 20.05.2025.
 */

sealed class SnackbarMessage {
    data object ContactSaved: SnackbarMessage()
    data object ContactCancelSaved: SnackbarMessage()

    data object ContactUndoDeleted: SnackbarMessage()
}