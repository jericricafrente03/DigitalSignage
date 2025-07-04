package com.jeric.bitteldigitalsignage.network.di

import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import com.jeric.bitteldigitalsignage.network.data.local.MeshDataBase
import com.jeric.bitteldigitalsignage.network.data.remote.IptvListAPI
import com.jeric.bitteldigitalsignage.network.data.repository.MeshRepositoryImpl
import com.jeric.bitteldigitalsignage.network.domain.repository.MeshRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMeshListRepository(
        apiService: IptvListAPI,
        dataStoreOperations: DataStoreOperations,
        meshDB: MeshDataBase,
    ): MeshRepository {
        return MeshRepositoryImpl(
            meshDataBase = meshDB,
            api = apiService,
            dataStoreOperations = dataStoreOperations
        )
    }
}