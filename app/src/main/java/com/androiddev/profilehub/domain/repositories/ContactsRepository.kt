package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface ContactsRepository {

    val contactsFlow: StateFlow<List<ContactUIEntity>>
    suspend fun loadContacts()
    fun deleteContactById(id: Long)
}