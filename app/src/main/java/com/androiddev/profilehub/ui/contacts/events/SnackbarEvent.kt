package com.androiddev.profilehub.ui.contacts.events

import com.androiddev.profilehub.R

/**
 * Created by Nadya N. on 20.05.2025.
 */

sealed class SnackbarEvent {

    sealed class Info : SnackbarEvent() {
        object ContactAdded : Info()
        data object ContactCancelAdd : Info()
        data object ContactNotDeleted : Info()
    }

    sealed class Actionable : SnackbarEvent() {
        abstract val textActionResId: Int
        abstract val onAction: () -> Unit

        data class ContactUndoDeleted(
            override val textActionResId: Int = R.string.contact_undo,
            override val onAction: () -> Unit,
        ) : Actionable()
    }

}