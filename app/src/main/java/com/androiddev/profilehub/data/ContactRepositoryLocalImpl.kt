package com.androiddev.profilehub.data

import com.androiddev.profilehub.domain.entities.ContactIndexedUIEntity
import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import com.androiddev.profilehub.ui.contacts.events.ContactsEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by Nadya N. on 14.05.2025.
 */
class ContactRepositoryLocalImpl @Inject constructor() : ContactsRepository {
    private val _contactsFlow = MutableStateFlow<List<ContactUIEntity>>(emptyList())
    override val contactsFlow: StateFlow<List<ContactUIEntity>> = _contactsFlow.asStateFlow()

    private val _eventsFlow = MutableSharedFlow<ContactsEvent>()
    override val eventsFlow = _eventsFlow.asSharedFlow()

    private var recentlyDeletedContact: ContactIndexedUIEntity? = null

    private fun getRandomId(): Long = Random.Default.nextLong()

    override suspend fun loadContacts() = withContext(Dispatchers.IO) {
        _eventsFlow.emit(ContactsEvent.Default)

        _contactsFlow.value = generateContacts()
        _eventsFlow.emit(ContactsEvent.Loaded)
    }

    private fun generateContacts(): List<ContactUIEntity> =
        listOf(
            ContactUIEntity(id = getRandomId(), name = "Ava Smith", career = "Photograph"),
            ContactUIEntity(id = getRandomId(), name = "Jackie Taylor", career = "Financier"),
            ContactUIEntity(id = getRandomId(), name = "Jessie Brown", career = "Actress"),
            ContactUIEntity(id = getRandomId(), name = "Jenny Walker", career = "Make up artist"),
            ContactUIEntity(id = getRandomId(), name = "Freddy Harris", career = "Secretary"),
            ContactUIEntity(id = getRandomId(), name = "Annie King", career = "Nurse"),
            ContactUIEntity(id = getRandomId(), name = "Liam Carter", career = "Architect"),
            ContactUIEntity(id = getRandomId(), name = "Olivia Johnson", career = "UX Designer"),
            ContactUIEntity(id = getRandomId(), name = "Noah Mitchell", career = "Software Engineer"),
            ContactUIEntity(id = getRandomId(), name = "Emma Robinson", career = "Dentist"),
            ContactUIEntity(id = getRandomId(), name = "Mason Lewis", career = "Lawyer"),
            ContactUIEntity(id = getRandomId(), name = "Sophia Hall", career = "Biologist"),
            ContactUIEntity(id = getRandomId(), name = "Ethan Young", career = "Chef"),
            ContactUIEntity(id = getRandomId(), name = "Isabella Scott", career = "Interior Designer"),
            ContactUIEntity(id = getRandomId(), name = "James Adams", career = "Mechanic"),
            ContactUIEntity(id = getRandomId(), name = "Mia Turner", career = "Journalist"),
        )

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