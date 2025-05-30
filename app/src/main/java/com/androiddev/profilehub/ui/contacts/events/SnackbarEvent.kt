package com.androiddev.profilehub.ui.contacts.events

/**
 * Created by Nadya N. on 20.05.2025.
 */

sealed class SnackbarEvent {
    data object ContactSaved : SnackbarEvent()
    data object ContactCancelSaved : SnackbarEvent()

    data object ContactUndoDeleted : SnackbarEvent()
}