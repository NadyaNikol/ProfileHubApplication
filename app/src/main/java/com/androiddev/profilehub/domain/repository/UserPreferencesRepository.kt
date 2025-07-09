package com.androiddev.profilehub.domain.repository

import com.androiddev.profilehub.data.AuthCredentials

/**
 * Created by Nadya N. on 22.04.2025.
 */
interface UserPreferencesRepository {
    suspend fun saveCredentials(credentials: AuthCredentials)
    suspend fun getSavedCredentials(): Result<AuthCredentials>
    suspend fun clearCredentials()
}