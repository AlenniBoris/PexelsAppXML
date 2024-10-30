package com.example.pexelsproject.domain.usecase.cache

import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.CacheCollectionsRepository
import com.example.pexelsproject.domain.repository.CachePhotosRepository
import javax.inject.Inject

class CacheSaveDataUseCase @Inject constructor(
    private val cachePhotosRepository: CachePhotosRepository,
    private val cacheCollectionsRepository: CacheCollectionsRepository
) {

    suspend fun invokeSavePhotos(photos: List<Photo>){
        cachePhotosRepository.deletePhotosFromCacheDatabase()
        cachePhotosRepository.addPhotosToCacheDatabase(photos)
    }

    suspend fun invokeSaveCollections(collections: List<Collection>){
        cacheCollectionsRepository.deleteCollectionsFromCacheDatabase()
        cacheCollectionsRepository.addCollectionsToCacheDatabase(collections)
    }

}