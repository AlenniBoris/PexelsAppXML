package com.example.pexelsproject.domain.usecase.cache

import android.content.Context
import android.util.Log
import com.example.pexelsproject.domain.repository.CacheCollectionsRepository
import com.example.pexelsproject.domain.repository.CachePhotosRepository
import com.example.pexelsproject.utils.ExtraFunctions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CacheClearDataUseCase @Inject constructor(
    private val cachePhotosRepository: CachePhotosRepository,
    private val cacheCollectionsRepository: CacheCollectionsRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun invokeDeleteCollections() {
        cacheCollectionsRepository.deleteCollectionsFromCacheDatabase()
        Log.d("COLLECTIONS=", "DELETED collections")
    }

    suspend fun invokeDeletePhotos() {
        cachePhotosRepository.deletePhotosFromCacheDatabase()
    }

    fun invokeDeleteQuery() {
        ExtraFunctions.writeLastQueryToFile("", context)
    }

}

