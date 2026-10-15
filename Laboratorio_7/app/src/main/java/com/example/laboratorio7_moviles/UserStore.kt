package com.example.laboratorio7_moviles

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserStore(private val context: Context) {
    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
    }

    val getName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    suspend fun deleteName() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NAME)
        }
    }
}
