package com.androiddev.profilehub.utils

import android.content.Context
import com.androiddev.profilehub.R
import com.androiddev.profilehub.ui.auth.errors.ValidationAuthError
import com.androiddev.profilehub.ui.contacts.errors.ValidationAddContactError
import com.androiddev.profilehub.ui.contacts.events.SnackbarEvent

/**
 * Created by Nadya N. on 09.05.2025.
 */

class UIMessageResolver(private val context: Context) {

    fun resolveSnackbarMessage(message: SnackbarEvent): String {
        return when (message) {
            is SnackbarEvent.Info.ContactAdded -> context.getString(R.string.contact_saved_success)
            is SnackbarEvent.Info.ContactCancelAdd -> context.getString(R.string.contact_does_not_save)
            is SnackbarEvent.Info.ContactNotDeleted -> context.getString(R.string.contact_not_deleted)

            is SnackbarEvent.Actionable.ContactUndoDeleted -> context.getString(R.string.contact_undo_deleted)
        }
    }

    fun resolveAddContactError(error: ValidationAddContactError): String {
        return when (error) {
            is ValidationAddContactError.None -> ""
            is ValidationAddContactError.Name -> resolveNameError(error)
            is ValidationAddContactError.Career -> resolveCareerError(error)
        }
    }

    private fun resolveNameError(error: ValidationAddContactError.Name): String {
        return when (error) {
            ValidationAddContactError.Name.EMPTY -> context.getString(R.string.name_must_not_be_blank)
            ValidationAddContactError.Name.INVALID -> context.getString(R.string.incorrect_name)
        }
    }

    private fun resolveCareerError(error: ValidationAddContactError.Career): String {
        return when (error) {
            ValidationAddContactError.Career.EMPTY -> context.getString(R.string.career_must_not_be_blank)
            ValidationAddContactError.Career.TOO_SHORT -> context.getString(
                R.string.career_must_include_a_minimum_characters,
                MIN_LENGTH_CAREER
            )
        }
    }

    fun resolveAuthError(error: ValidationAuthError): String {
        return when (error) {
            is ValidationAuthError.None -> ""
            is ValidationAuthError.Password -> resolvePasswordError(error)
            is ValidationAuthError.Email -> resolveEmailError(error)
        }
    }

    private fun resolvePasswordError(error: ValidationAuthError.Password): String {
        return when (error) {
            ValidationAuthError.Password.EMPTY -> context.getString(R.string.password_must_not_be_blank)
            ValidationAuthError.Password.TOO_SHORT -> context.getString(
                R.string.password_must_include_a_minimum_characters,
                MIN_LENGTH_PASSWORD
            )

            ValidationAuthError.Password.NON_ASCII -> context.getString(R.string.password_must_contain_only_english_letters_and_digits)
            ValidationAuthError.Password.NO_UPPERCASE -> context.getString(R.string.password_must_contain_at_least_one_uppercase_letter)
            ValidationAuthError.Password.NO_LETTER -> context.getString(R.string.password_need_to_contain_at_least_one_letter)
            ValidationAuthError.Password.NO_DIGIT -> context.getString(R.string.password_must_contain_at_least_one_digit)
            ValidationAuthError.Password.HAS_SPACE -> context.getString(R.string.password_must_not_contain_spaces)
        }
    }

    private fun resolveEmailError(error: ValidationAuthError.Email): String {
        return when (error) {
            ValidationAuthError.Email.EMPTY -> context.getString(R.string.e_mail_address_can_t_be_blank)
            ValidationAuthError.Email.INVALID -> context.getString(R.string.incorrect_e_mail_address)
        }
    }
}
