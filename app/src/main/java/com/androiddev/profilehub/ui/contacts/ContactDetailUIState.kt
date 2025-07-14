package com.androiddev.profilehub.ui.contacts

import com.androiddev.profilehub.domain.entity.ContactUIEntity

/**
 * Created by Nadya N. on 03.06.2025.
 */
data class ContactDetailUIState(
    val isLoading: Boolean = false,
    val contact: ContactUIEntity? = null,
) {
    val isNotFound
        get() = contact == null
}