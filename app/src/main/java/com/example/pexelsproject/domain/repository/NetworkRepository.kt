package com.example.pexelsproject.domain.repository

import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.model.Photo

interface NetworkRepository {

    suspend fun getPhotoById(id: Int): Photo?

    suspend fun getCuratedPhotosList(page: Int, perPage: Int): List<Photo>

    suspend fun getSearchedPhotosList(query: String, page: Int, perPage: Int): List<Photo>

    suspend fun getFeaturedCollectionsList(page: Int, perPage: Int): List<Collection>

}