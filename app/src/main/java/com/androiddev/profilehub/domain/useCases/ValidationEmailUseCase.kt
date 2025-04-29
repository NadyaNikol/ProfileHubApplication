package com.androiddev.profilehub.domain.useCases

import android.content.Context
import android.util.Patterns
import com.androiddev.profilehub.R

/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationEmailUseCase(private val context: Context) {

    operator fun invoke(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error(context.getString(R.string.e_mail_address_can_t_be_blank))
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult.Error(
                context.getString(R.string.incorrect_e_mail_address)
            )

            else -> ValidationResult.Success
        }
    }
}