package com.jeric.bitteldigitalsignage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jeric.bitteldigitalsignage.datastore.model.STB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.stbDataStore: DataStore<Preferences> by preferencesDataStore(name = "stb")

class DataStoreOperationImpl(context: Context) : DataStoreOperations {
    private val dataStore = context.stbDataStore

    private object PreferencesKey {
        val HOST = stringPreferencesKey("HTTPPreference_HOST")
        val ROOM = stringPreferencesKey("APIKeyPreference_PREF_ROOM")
        val PORT = stringPreferencesKey("HTTPPreference_PORT")
        val FIRST_RUN = stringPreferencesKey("firstrun")
        val MAC_ADDRESS = stringPreferencesKey("dev_id")
        val AREA_ID = stringPreferencesKey("area_id")
        val API_KEY = stringPreferencesKey("api_key")
        val END_DATE = stringPreferencesKey("END_DATE")
        val REMAINING_DAYS = stringPreferencesKey("REMAINING_DAYS")
        val GUEST_ASSIGN_ID = stringPreferencesKey("GUEST_ASSIGN_ID")
        val API_TOKEN = stringPreferencesKey("API_TOKEN")
        val TOKEN_EXPIRED_AT = stringPreferencesKey("TOKEN_EXPIRED_AT")
        val ROOM_ID = stringPreferencesKey("ROOM_ID")
        val ROOM_ASSIGNMENT = stringPreferencesKey("ROOM_ASSIGNMENT")
        val ROOM_UID = stringPreferencesKey("ROOM_UID")
    }

    override suspend fun saveStbState(stb: STB) {
        dataStore.edit { datastore ->
            stb.apply {
                datastore[PreferencesKey.ROOM] = ROOM
                datastore[PreferencesKey.HOST] = HOST
                datastore[PreferencesKey.PORT] = PORT
                datastore[PreferencesKey.FIRST_RUN] = FIRST_RUN
                datastore[PreferencesKey.MAC_ADDRESS] = MAC_ADDRESS
                datastore[PreferencesKey.AREA_ID] = AREA_ID
                datastore[PreferencesKey.API_KEY] = API_KEY
                datastore[PreferencesKey.END_DATE] = END_DATE
                datastore[PreferencesKey.REMAINING_DAYS] = REMAINING_DAYS
                datastore[PreferencesKey.GUEST_ASSIGN_ID] = GUEST_ASSIGN_ID
                datastore[PreferencesKey.API_TOKEN] = API_TOKEN
                datastore[PreferencesKey.TOKEN_EXPIRED_AT] = TOKEN_EXPIRED_AT
                datastore[PreferencesKey.ROOM_ID] = ROOM_ID
                datastore[PreferencesKey.ROOM_ASSIGNMENT] = ROOM_ASSIGNMENT
                datastore[PreferencesKey.ROOM_UID] = ROOM_UID
            }
        }
    }

    override fun readStbState(callback: (Flow<STB>) -> Unit) {
        callback.invoke(dataStore.data.map { pref ->
            STB.apply {
                ROOM = pref[PreferencesKey.ROOM] ?: ""
                HOST = pref[PreferencesKey.HOST] ?: "http://127.0.0.1"
                PORT = pref[PreferencesKey.PORT] ?: ""
                FIRST_RUN = pref[PreferencesKey.FIRST_RUN] ?: ""
                MAC_ADDRESS = pref[PreferencesKey.MAC_ADDRESS] ?: ""
                AREA_ID = pref[PreferencesKey.AREA_ID] ?: ""
                API_KEY = pref[PreferencesKey.API_KEY] ?: ""
                END_DATE = pref[PreferencesKey.END_DATE] ?: ""
                REMAINING_DAYS = pref[PreferencesKey.REMAINING_DAYS] ?: ""
                GUEST_ASSIGN_ID = pref[PreferencesKey.GUEST_ASSIGN_ID] ?: ""
                API_TOKEN = pref[PreferencesKey.API_TOKEN] ?: ""
                TOKEN_EXPIRED_AT = pref[PreferencesKey.TOKEN_EXPIRED_AT] ?: ""
                ROOM_ID = pref[PreferencesKey.ROOM_ID] ?: "null"
                ROOM_ASSIGNMENT = pref[PreferencesKey.ROOM_ASSIGNMENT] ?: "null"
                ROOM_UID = pref[PreferencesKey.ROOM_UID] ?: ""
            }
        })
    }


    override fun readStbFlow(
        dispatcher: CoroutineDispatcher,
        callback: (STB) -> Unit
    ) {
        readStbState { flow ->
            CoroutineScope(dispatcher).launch {
                flow.collect { stb ->
                    callback.invoke(stb)
                }
            }
        }
    }
}