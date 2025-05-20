package com.androiddev.profilehub.ui.contacts.events

import com.androiddev.profilehub.domain.entities.ContactUIEntity

/**
 * Created by Nadya N. on 20.05.2025.
 */
sealed class ContactEvent {
    data class SaveContact(val contact: ContactUIEntity) : ContactEvent()
    object CancelSaveContact : ContactEvent()
}