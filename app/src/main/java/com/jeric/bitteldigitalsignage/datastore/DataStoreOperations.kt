package com.jeric.bitteldigitalsignage.datastore

import com.jeric.bitteldigitalsignage.datastore.model.STB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveStbState(stb: STB)
    fun readStbState(callback: (Flow<STB>) -> Unit)
    fun readStbFlow(dispatcher: CoroutineDispatcher, callback: (STB) -> Unit)
}