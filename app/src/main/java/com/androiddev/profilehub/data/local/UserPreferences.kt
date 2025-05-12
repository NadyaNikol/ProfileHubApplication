package com.androiddev.profilehub.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Nadya N. on 18.04.2025.
 */

private const val DATASTORE_NAME = "user_prefs"
private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    val savedCredentials = context.dataStore.data.map { prefs ->
        AuthCredentials(
            email = prefs[EMAIL_KEY].orEmpty(),
            password = prefs[PASSWORD_KEY].orEmpty(),
            isRememberMe = prefs[REMEMBER_ME_KEY] ?: false
        )
    }

    suspend fun saveCredentials(credentials: AuthCredentials) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = credentials.email
            prefs[PASSWORD_KEY] = credentials.password
            prefs[REMEMBER_ME_KEY] = credentials.isRememberMe
        }
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { prefs ->
            prefs.remove(EMAIL_KEY)
            prefs.remove(PASSWORD_KEY)
            prefs.remove(REMEMBER_ME_KEY)
        }
    }

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("user_email")
        private val PASSWORD_KEY = stringPreferencesKey("user_password")
        private val REMEMBER_ME_KEY = booleanPreferencesKey("user_remember_me")
    }
}