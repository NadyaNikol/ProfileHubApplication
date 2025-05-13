package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.domain.entities.ContactUIEntity

/**
 * Created by Nadya N. on 13.05.2025.
 */
interface ContactsRepository {

    suspend fun getContacts(): List<ContactUIEntity>
}