package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
class GetContactsUseCase @Inject constructor(
    private val repository: ContactsRepository,
) {
    val contactsFlow: Flow<List<ContactUIEntity>> = repository.contactsFlow
    suspend fun loadContacts() = repository.loadContacts()
    fun deleteContactById(id: Long) = repository.deleteContactById(id)
}