package com.androiddev.profilehub.ui.contacts.events

import com.androiddev.profilehub.domain.entities.ContactUIEntity

/**
 * Created by Nadya N. on 23.05.2025.
 */
sealed class UiEvent {
    sealed class Undo : UiEvent() {
        object Clicked : Undo()
        object Dismissed : Undo()
    }

    sealed class ContactDialog : UiEvent() {
        data class Save(val contact: ContactUIEntity) : ContactDialog()
        object Cancel : ContactDialog()
    }

    data class SwipeDelete(val id: Long) : UiEvent()
    object ClearSnackbarMessage : UiEvent()
}
