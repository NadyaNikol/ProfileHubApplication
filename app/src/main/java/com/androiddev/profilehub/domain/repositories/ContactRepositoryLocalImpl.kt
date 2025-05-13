package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.domain.entities.ContactUIEntity

/**
 * Created by Nadya N. on 14.05.2025.
 */
class ContactRepositoryLocalImpl() : ContactsRepository {
    override suspend fun getContacts(): List<ContactUIEntity> {

        val list = mutableListOf<ContactUIEntity>()
        list.apply {
            add(ContactUIEntity(name = "Ava Smith", career = "Photograph"))
            add(ContactUIEntity(name = "Jessie Brown", career = "Actress"))
            add(ContactUIEntity(name = "Jackie Taylor", career = "Financier"))
            add(ContactUIEntity(name = "Jenny Walker", career = "Make up artist"))
            add(ContactUIEntity(name = "Freddy Harris", career = "Secretary"))
            add(ContactUIEntity(name = "Annie King", career = "Nurse"))
            add(ContactUIEntity(name = "Liam Carter", career = "Architect"))
            add(ContactUIEntity(name = "Olivia Johnson", career = "UX Designer"))
            add(ContactUIEntity(name = "Noah Mitchell", career = "Software Engineer"))
            add(ContactUIEntity(name = "Emma Robinson", career = "Dentist"))
            add(ContactUIEntity(name = "Mason Lewis", career = "Lawyer"))
            add(ContactUIEntity(name = "Sophia Hall", career = "Biologist"))
            add(ContactUIEntity(name = "Ethan Young", career = "Chef"))
            add(ContactUIEntity(name = "Isabella Scott", career = "Interior Designer"))
            add(ContactUIEntity(name = "James Adams", career = "Mechanic"))
            add(ContactUIEntity(name = "Mia Turner", career = "Journalist"))
        }
        return list
    }
}