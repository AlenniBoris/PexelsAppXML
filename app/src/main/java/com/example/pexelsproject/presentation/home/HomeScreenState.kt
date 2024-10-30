package com.example.pexelsproject.presentation.home

import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.model.Photo

data class HomeScreenState(
    val photos: List<Photo> = emptyList(),
    val errorState: Boolean = false,
    val isActive: Boolean = false,
    val history: Set<String> = emptySet(),
    val queryText: String = "",
    val cachedQuery: String = "",
    val featuredCollections: List<Collection> = listOf(
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
        Collection(
            id = "",
            title = "",
            description = "",
            isPrivate = false,
            mediaCount = 0,
            photosCount = 0,
            videosCount = 0
        ),
    ),
    val initialFeaturedCollections: List<Collection> = emptyList(),
    val selectedFeaturedCollectionId: String = "",
)