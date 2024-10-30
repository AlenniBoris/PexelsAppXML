package com.example.pexelsproject.domain.usecase.cache

import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.CacheCollectionsRepository
import com.example.pexelsproject.domain.repository.CachePhotosRepository
import javax.inject.Inject

class CacheGetAllDataUseCase @Inject constructor(
    private val cachePhotosRepository: CachePhotosRepository,
    private val cacheCollectionsRepository: CacheCollectionsRepository
) {

    suspend fun invoke() : Pair<List<Photo>, List<Collection>>{
        return Pair(
            cachePhotosRepository.getAllPhotosFromCacheDatabase(),
            cacheCollectionsRepository.getAllCollectionsFromCacheDatabase()
        )
    }

}