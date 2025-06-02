package com.androiddev.profilehub.ui.contacts

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent

/**
 * Created by Nadya N. on 08.05.2025.
 */
data class ContactsState(
    val items: List<ContactUIEntity> = listOf(),
    val snackbarEvent: SnackbarEvent? = null,
    val loadingState: LoadingState = LoadingState.Idle,
    val isLoading: Boolean = false,
) {
    val isNoDataVisible get() = items.isEmpty()
}