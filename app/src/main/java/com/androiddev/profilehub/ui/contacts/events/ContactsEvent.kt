package com.androiddev.profilehub.ui.contacts.events

/**
 * Created by Nadya N. on 30.05.2025.
 */
sealed class ContactsEvent {
    object Default : ContactsEvent()

    object Loaded : ContactsEvent()
    object ContactAdded : ContactsEvent()
    object ContactDeleted : ContactsEvent()
    object ContactDeleteUndone : ContactsEvent()
}