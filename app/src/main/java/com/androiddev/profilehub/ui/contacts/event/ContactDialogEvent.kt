package com.androiddev.profilehub.ui.contacts.event

import com.androiddev.profilehub.domain.entity.ContactUIEntity

/**
 * Created by Nadya N. on 03.06.2025.
 */
sealed class ContactDialogEvent {
    data class Add(val contact: ContactUIEntity) : ContactDialogEvent()
    data object Cancel : ContactDialogEvent()
}