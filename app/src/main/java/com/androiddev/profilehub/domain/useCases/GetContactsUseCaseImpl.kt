package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
class GetContactsUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository,
): GetContactsUseCase {
    override val contactsFlow: Flow<List<ContactUIEntity>> = repository.contactsFlow
    override suspend fun loadContacts() = repository.loadContacts()
}