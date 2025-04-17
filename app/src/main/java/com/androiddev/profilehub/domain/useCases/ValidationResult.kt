package com.androiddev.profilehub.domain.useCases

/**
 * Created by Nadya N. on 07.04.2025.
 */
sealed class ValidationResult {
    data class Success(val data: String) : ValidationResult()
    data class Error(val errorMessage: Throwable) : ValidationResult()
}