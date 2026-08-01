package br.com.fabfdev.rocketia.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val USER_SETTINGS_DATASTORE_NAME = "user_settings"

private const val SELECTED_STACK_KEY = "selected_stack"
private val SELECTED_STACK_PREFERENCES_KEY = stringPreferencesKey(SELECTED_STACK_KEY)

private const val FIRST_LAUNCH_KEY = "first_launch"

class UserSettingsDataStorePreferencesImpl @Inject constructor(
    private val context: Context,
) : UserSettingsPreferences {

    val Context.datastore: DataStore<Preferences> by preferencesDataStore(
        name = USER_SETTINGS_DATASTORE_NAME
    )

    override val selectedStack: Flow<String?>
        get() = context.datastore.data.map { preferences ->
            preferences[SELECTED_STACK_PREFERENCES_KEY]
        }

    override suspend fun changeSelectedStack(stack: String) {
        context.datastore.edit { settings ->
            settings[SELECTED_STACK_PREFERENCES_KEY] = stack
        }
    }

}