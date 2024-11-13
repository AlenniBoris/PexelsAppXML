package com.example.pexelsproject.data.repository

import com.example.pexelsproject.data.mappers.asCollection
import com.example.pexelsproject.data.mappers.asCollectionEntity
import com.example.pexelsproject.data.source.dao.cache.CacheCollectionsDatabase
import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.repository.CacheCollectionsRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheCollectionsRepositoryImpl @Inject constructor(
    private val cacheCollectionsDatabase: CacheCollectionsDatabase
) : CacheCollectionsRepository {

    override suspend fun addCollectionsToCacheDatabase(collectionsToAdd: List<Collection>) {
        runBlocking {
            collectionsToAdd.forEach { it ->
                cacheCollectionsDatabase.dao.addCollectionToDatabase(it.asCollectionEntity())
            }
        }
    }

    override suspend fun deleteCollectionsFromCacheDatabase() {
        cacheCollectionsDatabase.dao.clearTable()
    }

    override suspend fun getAllCollectionsFromCacheDatabase(): List<Collection> {
        return cacheCollectionsDatabase.dao.getAllCollectionsFromDatabase()
            .map { it.asCollection() }
    }

}