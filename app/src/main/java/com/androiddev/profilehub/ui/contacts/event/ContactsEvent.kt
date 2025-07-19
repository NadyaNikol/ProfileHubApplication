package com.androiddev.profilehub.ui.contacts.event

/**
 * Created by Nadya N. on 30.05.2025.
 */
sealed class ContactsEvent {
    data object ContactAdded : ContactsEvent()
    data object ContactCancelAdd : ContactsEvent()
    data object ContactDeleted : ContactsEvent()
    data object ContactDeleteUndone : ContactsEvent()
}