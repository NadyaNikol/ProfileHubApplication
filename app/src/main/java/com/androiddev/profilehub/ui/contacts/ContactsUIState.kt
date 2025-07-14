package com.androiddev.profilehub.ui.contacts

import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.event.SnackbarEvent

/**
 * Created by Nadya N. on 08.05.2025.
 */
data class ContactsUIState(
    val items: List<ContactUIEntity> = listOf(),
    val snackbarEvent: SnackbarEvent? = null,
    val loadingState: LoadingState = LoadingState.Idle,
) {
    val isNoDataVisible
        get() = items.isEmpty()
}