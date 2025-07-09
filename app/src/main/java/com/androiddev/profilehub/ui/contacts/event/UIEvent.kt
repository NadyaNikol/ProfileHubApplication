package com.androiddev.profilehub.ui.contacts.event

/**
 * Created by Nadya N. on 23.05.2025.
 */
sealed class UiEvent {
    sealed class Undo : UiEvent() {
        data object Clicked : Undo()
    }

    data class SwipeDelete(val id: Long) : UiEvent()
    data object ClearSnackbarMessage : UiEvent()
}
