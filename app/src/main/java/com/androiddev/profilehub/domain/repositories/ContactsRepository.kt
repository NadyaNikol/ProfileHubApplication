package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.ui.contacts.events.ContactsEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface ContactsRepository {

    val contactsFlow: StateFlow<List<ContactUIEntity>>
    val eventsFlow: SharedFlow<ContactsEvent>
    suspend fun loadContacts()
    suspend fun deleteContactById(id: Long)
    suspend fun addContact(contact: ContactUIEntity)
    suspend fun undoDelete()
}