package com.androiddev.profilehub.utils

import com.androiddev.profilehub.domain.entities.ContactUIEntity
import kotlin.random.Random

/**
 * Created by Nadya N. on 03.06.2025.
 */

enum class Gender { MALE, FEMALE }

object RandomContactGenerator {

    private val maleNames = listOf(
        "Liam",
        "Noah",
        "Oliver",
        "Ethan",
        "James",
        "Mason",
        "Logan",
        "Lucas",
        "Jack",
        "Freddy"
    )
    private val femaleNames = listOf(
        "Emma",
        "Olivia",
        "Ava",
        "Sophia",
        "Isabella",
        "Mia",
        "Amelia",
        "Ella",
        "Annie",
        "Jenny"
    )
    private val surnames = listOf(
        "Smith",
        "Johnson",
        "Williams",
        "Brown",
        "Jones",
        "Miller",
        "Davis",
        "Taylor",
        "Hall",
        "Walker"
    )
    private val careers = listOf(
        "Photographer",
        "Financier",
        "Actress",
        "Secretary",
        "Nurse",
        "Architect",
        "UX Designer",
        "Software Engineer",
        "Dentist",
        "Lawyer",
        "Biologist",
        "Chef",
        "Interior Designer",
        "Mechanic",
        "Journalist"
    )

    private val random = java.util.Random()

    private fun getRandomId(): Long = Random.Default.nextLong()

    private fun randomGender(): Gender = if (random.nextBoolean()) Gender.MALE else Gender.FEMALE

    private fun getRandomName(gender: Gender): String {
        val firstName = when (gender) {
            Gender.MALE -> maleNames.random()
            Gender.FEMALE -> femaleNames.random()
        }
        val lastName = surnames.random()
        return "$firstName $lastName"
    }

    private fun getImageUrl(gender: Gender): String {
        val num = random.nextInt(100)
        return when (gender) {
            Gender.MALE -> "https://randomuser.me/api/portraits/men/$num.jpg"
            Gender.FEMALE -> "https://randomuser.me/api/portraits/women/$num.jpg"
        }
    }

    private fun getRandomCareer(): String = careers.random()

    fun getRandom(): ContactUIEntity {
        val gender = randomGender()
        val fullName = getRandomName(gender)
        val career = getRandomCareer()
        val imageUrl = getImageUrl(gender)

        return ContactUIEntity(
            id = getRandomId(),
            name = fullName,
            career = career,
            image = imageUrl
        )
    }
}