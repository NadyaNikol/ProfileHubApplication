package com.androiddev.profilehub.ui.contacts.events

import com.androiddev.profilehub.domain.entities.ContactUIEntity

/**
 * Created by Nadya N. on 03.06.2025.
 */
sealed class ContactDialogEvent {
    data class Save(val contact: ContactUIEntity) : ContactDialogEvent()
    object Cancel : ContactDialogEvent()
}