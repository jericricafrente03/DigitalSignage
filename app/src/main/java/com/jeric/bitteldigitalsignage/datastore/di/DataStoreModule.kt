package com.jeric.bitteldigitalsignage.datastore.di

import android.content.Context
import com.jeric.bitteldigitalsignage.datastore.DataStoreOperationImpl
import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationImpl(context = context)
    }
}
