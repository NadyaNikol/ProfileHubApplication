package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.data.local.AuthCredentials

/**
 * Created by Nadya N. on 22.04.2025.
 */
interface UserPreferencesRepository {
    suspend fun saveCredentials(credentials: AuthCredentials)
    suspend fun getSavedCredentials(): Result<AuthCredentials>
    suspend fun clearCredentials()
}