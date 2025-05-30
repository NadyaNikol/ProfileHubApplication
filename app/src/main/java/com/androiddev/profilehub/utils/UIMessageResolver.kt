package com.androiddev.profilehub.utils

import android.content.Context
import com.androiddev.profilehub.R
import com.androiddev.profilehub.ui.auth.errors.AuthError
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent

/**
 * Created by Nadya N. on 09.05.2025.
 */

class UIMessageResolver(private val context: Context) {

    fun resolveSnackbarMessage(message: SnackbarEvent): String {
        return when (message) {
            is SnackbarEvent.ContactSaved -> context.getString(R.string.contact_saved_success)
            is SnackbarEvent.ContactCancelSaved -> context.getString(R.string.contact_does_not_save)
            is SnackbarEvent.ContactUndoDeleted -> context.getString(R.string.contact_undo_deleted)
        }
    }

    fun resolveAuthError(error: AuthError): String {
        return when (error) {
            is AuthError.None -> ""
            is AuthError.Password -> resolvePasswordError(error)
            is AuthError.Email -> resolveEmailError(error)
        }
    }

    private fun resolvePasswordError(error: AuthError.Password): String {
        return when (error) {
            AuthError.Password.EMPTY -> context.getString(R.string.password_must_not_be_blank)
            AuthError.Password.TOO_SHORT -> context.getString(R.string.password_must_include_a_minimum_characters)
            AuthError.Password.NON_ASCII -> context.getString(R.string.password_must_contain_only_english_letters_and_digits)
            AuthError.Password.NO_UPPERCASE -> context.getString(R.string.password_must_contain_at_least_one_uppercase_letter)
            AuthError.Password.NO_LETTER -> context.getString(R.string.password_need_to_contain_at_least_one_letter)
            AuthError.Password.NO_DIGIT -> context.getString(R.string.password_must_contain_at_least_one_digit)
            AuthError.Password.HAS_SPACE -> context.getString(R.string.password_must_not_contain_spaces)
        }
    }

    private fun resolveEmailError(error: AuthError.Email): String {
        return when (error) {
            AuthError.Email.EMPTY -> context.getString(R.string.e_mail_address_can_t_be_blank)
            AuthError.Email.INVALID -> context.getString(R.string.incorrect_e_mail_address)
        }
    }
}
