/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 4:38 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 4:38 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 07/07/22, 4:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 4:04 PM
 ************************************************/

package health.care.ai.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager(private val context: Context) {

//TODO here false is light, true is dark

    suspend fun setMiddlevareToken(type: String) {
        context.dataStore.edit { preferences ->
            preferences[middlevareToken] = type
        }
    }

    val getMiddlevareToken: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[middlevareToken] ?: ""
        }
    suspend fun setAbhaToken(type: String) {
        context.dataStore.edit { preferences ->
            preferences[abhaToken] = type
        }
    }

    val getAbhaToken: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[abhaToken] ?: ""
        }



    suspend fun setLanguage(type: String) {
        context.dataStore.edit { preferences ->
            preferences[language] = type
        }
    }

    val getlanguage: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[language] ?: ""
        }



    suspend fun setTxnId(type: String) {
        context.dataStore.edit { preferences ->
            preferences[txnId] = type
        }
    }

    val getTxnId: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[txnId] ?: ""
        }


    suspend fun setbeniId(type: Int) {
        context.dataStore.edit { preferences ->
            preferences[beniId] = type
        }
    }

    val getbeniId: Flow<Int>
        get() = context.dataStore.data.map { preferences ->
            preferences[beniId] ?: 0
        }

    companion object {
        private const val DATASTORE_NAME = "nearmedb"

        private val middlevareToken = stringPreferencesKey("middlevareToken");
        private val language = stringPreferencesKey("language");
        private val abhaToken = stringPreferencesKey("abhaToken");
        private val txnId = stringPreferencesKey("txnId");
        private val beniId = intPreferencesKey("beniId");
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}