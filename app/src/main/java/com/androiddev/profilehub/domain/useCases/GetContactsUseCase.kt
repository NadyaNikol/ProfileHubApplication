package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import com.androiddev.profilehub.domain.repositories.ContactsRepository
import javax.inject.Inject

/**
 * Created by Nadya N. on 13.05.2025.
 */
class GetContactsUseCase @Inject constructor(
    private val repository: ContactsRepository,
) {
    suspend operator fun invoke(): List<ContactUIEntity> {
        return repository.getContacts()
    }
}