package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface GetContactsUseCase {
    val contactsFlow: Flow<List<ContactUIEntity>>
    suspend fun loadContacts()
}