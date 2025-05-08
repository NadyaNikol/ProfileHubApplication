package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.data.local.AuthCredentials
import com.androiddev.profilehub.data.local.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Nadya N. on 22.04.2025.
 */

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
) : UserPreferencesRepository {

    override val savedCredentials: Flow<AuthCredentials> = userPreferences.savedCredentials

    override suspend fun saveCredentials(credentials: AuthCredentials) {
        userPreferences.saveCredentials(credentials)
    }

    override suspend fun clearCredentials() {
        userPreferences.clearCredentials()
    }

}