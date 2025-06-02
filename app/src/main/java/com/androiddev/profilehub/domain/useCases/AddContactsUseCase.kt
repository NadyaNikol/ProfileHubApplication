package com.androiddev.profilehub.domain.useCases

import com.androiddev.profilehub.domain.entities.ContactUIEntity

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface AddContactsUseCase {
    suspend fun addContact(contact: ContactUIEntity)
}