package com.androiddev.profilehub.ui.contacts.events

/**
 * Created by Nadya N. on 07.04.2025.
 */
sealed class AddContactFormEvent {
    data class NameChanged(val name: String) : AddContactFormEvent()
    data class CareerChanged(val career: String) : AddContactFormEvent()
}