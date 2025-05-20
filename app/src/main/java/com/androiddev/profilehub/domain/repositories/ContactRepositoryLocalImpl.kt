package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by Nadya N. on 14.05.2025.
 */
class ContactRepositoryLocalImpl @Inject constructor() : ContactsRepository {
    private val _contactsFlow = MutableStateFlow<List<ContactUIEntity>>(emptyList())
    override val contactsFlow = _contactsFlow.asStateFlow()

    private fun getRandomId(): Long = Random.nextLong()

    override suspend fun loadContacts() = withContext(Dispatchers.Default) {
        _contactsFlow.value = generateContacts()
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

    override fun deleteContactById(id: Long) {
        _contactsFlow.value = _contactsFlow.value.filterNot { it.id == id }
    }

    override fun saveContact(contact: ContactUIEntity) {
        _contactsFlow.value = _contactsFlow.value.plus(contact)
    }
}