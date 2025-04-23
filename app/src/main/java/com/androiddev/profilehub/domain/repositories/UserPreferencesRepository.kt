package com.androiddev.profilehub.domain.repositories

import com.androiddev.profilehub.data.local.AuthCredentials
import kotlinx.coroutines.flow.Flow

/**
 * Created by Nadya N. on 22.04.2025.
 */
interface UserPreferencesRepository {
    val savedEmail: Flow<String?>
    val savedPassword: Flow<String?>
    val savedCredentials: Flow<AuthCredentials?>
    suspend fun saveCredentials(credentials: AuthCredentials)
    suspend fun clearCredentials()
}