package com.example.feature_xml_userlist.domain.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(
    private val context: Context
) {
    private val COUNTER_KEY = intPreferencesKey("counter")

    suspend fun saveCounter(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[COUNTER_KEY] = value
        }
    }

    val counterFlow = context.dataStore.data.map { preferences ->
        preferences[COUNTER_KEY] ?: 0
    }
}