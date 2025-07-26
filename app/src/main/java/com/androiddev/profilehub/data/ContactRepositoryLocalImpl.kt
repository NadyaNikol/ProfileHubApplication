package com.androiddev.profilehub.data

import com.androiddev.profilehub.domain.entity.ContactIndexedUIEntity
import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.domain.repository.ContactsRepository
import com.androiddev.profilehub.ui.main.contacts.event.ContactsEvent
import com.androiddev.profilehub.util.RandomContactGenerator
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

class ContactRepositoryLocalImpl @Inject constructor() : ContactsRepository {
    private val contactsCount = 20

    private val _contactsFlow = MutableStateFlow<List<ContactUIEntity>>(emptyList())
    override val contactsFlow: StateFlow<List<ContactUIEntity>> = _contactsFlow.asStateFlow()

    private val _eventsFlow = MutableSharedFlow<ContactsEvent>()
    override val eventsFlow = _eventsFlow.asSharedFlow()

    private var recentlyDeletedContact: ContactIndexedUIEntity? = null

    override suspend fun loadContacts() = withContext(Dispatchers.IO) {
        _contactsFlow.value = generateContacts(contactsCount)
    }

    private fun generateContacts(count: Int): List<ContactUIEntity> =
        List(count) { RandomContactGenerator.getRandom() }

    override suspend fun deleteContactById(id: Long) = withContext(Dispatchers.IO) {
        val currentList = _contactsFlow.value
        val index = currentList.indexOfFirst { it.id == id }
        if (index == -1) return@withContext
        val contact = currentList[index]

        recentlyDeletedContact = ContactIndexedUIEntity(
            contact = contact,
            index = index
        )

        _contactsFlow.value = currentList.minusElement(contact)
        _eventsFlow.emit(ContactsEvent.ContactDeleted)
    }

    override suspend fun undoDelete(): Unit = withContext(Dispatchers.IO) {
        recentlyDeletedContact?.let { contact ->
            val index = contact.index
            val currentList = _contactsFlow.value

            val newList = currentList.toMutableList().apply {
                add(index.coerceAtMost(size), contact.contact)
            }
            _contactsFlow.value = newList
            recentlyDeletedContact = null
            _eventsFlow.emit(ContactsEvent.ContactDeleteUndone)
        }
    }

    override suspend fun addContact(contact: ContactUIEntity) = withContext(Dispatchers.IO) {
        _contactsFlow.value = _contactsFlow.value.plus(contact)
        _eventsFlow.emit(ContactsEvent.ContactAdded)
    }

    override suspend fun emitCancelContactSaved() = withContext(Dispatchers.IO) {
        _eventsFlow.emit(ContactsEvent.ContactCancelAdd)
    }

    override suspend fun getContactById(itemId: Long): ContactUIEntity? =
        withContext(Dispatchers.IO) {
            _contactsFlow.value.find { it.id == itemId }
        }
}