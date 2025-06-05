package com.androiddev.profilehub.ui.contacts

import com.androiddev.profilehub.ui.contacts.errors.ValidationAddContactError

/**
 * Created by Nadya N. on 03.06.2025.
 */
data class AddContactsState(
    val name: String = "",
    val nameError: ValidationAddContactError = ValidationAddContactError.None,
    val career: String = "",
    val careerError: ValidationAddContactError = ValidationAddContactError.None,

    val submitDataEvent: Unit? = null,
)