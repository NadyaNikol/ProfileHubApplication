package com.androiddev.profilehub.domain.useCase

import com.androiddev.profilehub.domain.entity.ContactUIEntity
import com.androiddev.profilehub.domain.repository.ContactsRepository
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface AddContactUseCase {
    suspend operator fun invoke(contact: ContactUIEntity)
}

class AddContactUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository,
) : AddContactUseCase {
    override suspend fun invoke(contact: ContactUIEntity) = repository.addContact(contact)
}