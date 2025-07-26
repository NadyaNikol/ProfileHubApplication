package com.androiddev.profilehub.ui.main.contacts

import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.ui.main.contacts.event.SnackbarEvent

/**
 * Created by Nadya N. on 08.05.2025.
 */
data class ContactsUIState(
    val items: List<ContactUIEntity> = listOf(),
    val snackbarEvent: SnackbarEvent? = null,
    val isLoading: Boolean = false,
) {
    val isNoDataVisible
        get() = items.isEmpty()
}