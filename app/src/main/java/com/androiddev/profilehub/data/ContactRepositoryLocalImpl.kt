package com.androiddev.profilehub.data

import com.androiddev.profilehub.domain.entities.ContactIndexedUIEntity
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import com.androiddev.profilehub.ui.contacts.events.ContactsEvent
import com.androiddev.profilehub.utils.RandomContactGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Nadya N. on 14.05.2025.
 */

private const val CONTACTS_COUNT = 10

class ContactRepositoryLocalImpl @Inject constructor() : ContactsRepository {
    private val _contactsFlow = MutableStateFlow<List<ContactUIEntity>>(emptyList())
    override val contactsFlow: StateFlow<List<ContactUIEntity>> = _contactsFlow.asStateFlow()

    private val _eventsFlow = MutableSharedFlow<ContactsEvent>()
    override val eventsFlow = _eventsFlow.asSharedFlow()

    private var recentlyDeletedContact: ContactIndexedUIEntity? = null

    override suspend fun loadContacts() = withContext(Dispatchers.IO) {
        _eventsFlow.emit(ContactsEvent.Default)

        _contactsFlow.value = generateContacts(CONTACTS_COUNT)
        _eventsFlow.emit(ContactsEvent.Loaded)
    }

    private fun generateContacts(count: Int): List<ContactUIEntity> =
        List(count) { RandomContactGenerator.getRandom() }

    override suspend fun deleteContactById(id: Long) {
        val currentList = _contactsFlow.value
        val index = currentList.indexOfFirst { it.id == id }
        if (index == -1) return

        recentlyDeletedContact = ContactIndexedUIEntity(
            contact = currentList[index],
            index = index
        )

        _contactsFlow.value = currentList.filterNot { it.id == id }
        _eventsFlow.emit(ContactsEvent.ContactDeleted)
    }

    override suspend fun undoDelete() {
        val contact = recentlyDeletedContact ?: return
        val index = contact.index
        val currentList = _contactsFlow.value

        val newList = currentList.toMutableList().apply {
            add(index.coerceAtMost(size), contact.contact)
        }
        _contactsFlow.value = newList
        recentlyDeletedContact = null
        _eventsFlow.emit(ContactsEvent.ContactDeleteUndone)
    }

    override suspend fun addContact(contact: ContactUIEntity) {
        _contactsFlow.value = _contactsFlow.value.plus(contact)
        _eventsFlow.emit(ContactsEvent.ContactAdded)
    }

    override suspend fun emitCancelContactSaved() {
        _eventsFlow.emit(ContactsEvent.ContactCancelAdd)
    }
}