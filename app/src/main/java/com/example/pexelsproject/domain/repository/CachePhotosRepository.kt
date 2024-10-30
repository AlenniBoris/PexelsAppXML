package com.example.pexelsproject.domain.repository

import com.example.pexelsproject.domain.model.Photo

interface CachePhotosRepository {

    suspend fun addPhotosToCacheDatabase(photosToAdd: List<Photo>)

    suspend fun deletePhotosFromCacheDatabase()

    suspend fun getAllPhotosFromCacheDatabase(): List<Photo>

}