package com.androiddev.profilehub.data

import com.androiddev.profilehub.data.local.AuthCredentials
import com.androiddev.profilehub.data.local.UserPreferences
import com.androiddev.profilehub.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Nadya N. on 22.04.2025.
 */

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
) : UserPreferencesRepository {

    override suspend fun saveCredentials(credentials: AuthCredentials) =
        withContext(Dispatchers.Default) {
            userPreferences.saveCredentials(credentials)
        }

    override suspend fun getSavedCredentials() = withContext(Dispatchers.Default) {
        return@withContext runCatching {
            userPreferences.savedCredentials.first()
        }
    }

    override suspend fun clearCredentials() = withContext(Dispatchers.Default) {
        userPreferences.clearCredentials()
    }
}