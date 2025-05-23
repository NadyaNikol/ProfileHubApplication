package com.androiddev.profilehub.ui.contacts

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.messages.SnackbarMessage

/**
 * Created by Nadya N. on 08.05.2025.
 */
data class ContactsState(
    val items: List<ContactUIEntity> = listOf(),
    val snackbarMessage: SnackbarMessage? = null,
    val isLoading: Boolean = false,
    val isEndOfList: Boolean = false,
)

) {
}