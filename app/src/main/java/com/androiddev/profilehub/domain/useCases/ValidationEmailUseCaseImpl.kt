package com.androiddev.profilehub.domain.useCases

import android.util.Patterns
import com.androiddev.profilehub.ui.auth.errors.AuthError
import javax.inject.Inject

/**
 * Created by Nadya N. on 07.04.2025.
 */
class ValidationEmailUseCaseImpl @Inject constructor() : ValidationEmailUseCase {

    override fun validate(email: String): AuthError {
        return when {
            email.isBlank() -> AuthError.Email.EMPTY
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> AuthError.Email.INVALID

            else -> AuthError.None
        }
    }
}