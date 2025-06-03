package com.androiddev.profilehub.ui.contacts.events

/**
 * Created by Nadya N. on 23.05.2025.
 */
sealed class UiEvent {
    sealed class Undo : UiEvent() {
        object Clicked : Undo()
    }

    data class SwipeDelete(val id: Long) : UiEvent()
    object ClearSnackbarMessage : UiEvent()
}
