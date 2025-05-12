package com.androiddev.profilehub.utils

/**
 * Created by Nadya N. on 17.04.2025.
 */
object EmailParser {
    fun extractName(email: String): String {
        val namePart = email.substringBefore("@")
        val parts = namePart.split(".", "_", "-")

        val capitalizedParts = parts.map { it.replaceFirstChar { char -> char.uppercase() } }

        return capitalizedParts.joinToString(" ")
    }
}