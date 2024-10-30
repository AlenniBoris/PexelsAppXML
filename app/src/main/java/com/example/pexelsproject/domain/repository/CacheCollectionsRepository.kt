package com.example.pexelsproject.domain.repository

import com.example.pexelsproject.domain.model.Collection

interface CacheCollectionsRepository {

    suspend fun addCollectionsToCacheDatabase(collectionsToAdd: List<Collection>)

    suspend fun deleteCollectionsFromCacheDatabase()

    suspend fun getAllCollectionsFromCacheDatabase(): List<Collection>

}