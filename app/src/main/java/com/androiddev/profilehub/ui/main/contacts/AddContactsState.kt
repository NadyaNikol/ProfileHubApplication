package com.androiddev.profilehub.ui.main.contacts

import com.androiddev.profilehub.domain.error.ValidationAddContactError

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