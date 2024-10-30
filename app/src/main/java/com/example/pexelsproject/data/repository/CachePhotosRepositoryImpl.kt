package com.example.pexelsproject.data.repository

import com.example.pexelsproject.data.mappers.asPhoto
import com.example.pexelsproject.data.mappers.asPhotoEntity
import com.example.pexelsproject.data.source.dao.cache.CachePhotosDatabase
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.CachePhotosRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachePhotosRepositoryImpl @Inject constructor(
    private val cachePhotosDatabase: CachePhotosDatabase
): CachePhotosRepository{

    override suspend fun addPhotosToCacheDatabase(photosToAdd: List<Photo>) {
        runBlocking {
            photosToAdd.forEach { it ->
                cachePhotosDatabase.dao.addPhotoToDatabase(it.asPhotoEntity())
            }
        }
    }

    override suspend fun deletePhotosFromCacheDatabase() {
        cachePhotosDatabase.dao.clearTable()
    }

    override suspend fun getAllPhotosFromCacheDatabase(): List<Photo> {
        return cachePhotosDatabase.dao.getAllPhotosFromDatabase().map { it.asPhoto() }
    }

}