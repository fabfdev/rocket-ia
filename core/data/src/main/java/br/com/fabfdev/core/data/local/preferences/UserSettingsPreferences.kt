package br.com.fabfdev.core.data.local.preferences

import kotlinx.coroutines.flow.Flow

interface UserSettingsPreferences {
    val selectedStack: Flow<String?>
    suspend fun changeSelectedStack(stack: String)
}