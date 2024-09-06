package com.example.pexelsproject.screens.main

import com.example.pexelsproject.data.model.Collection
import com.example.pexelsproject.data.model.Photo

data class MainScreenState(
    val photos: List<Photo> = emptyList(),
    val errorState: Boolean = false,
    val isActive: Boolean = false,
    val history: Set<String> = emptySet(),
    val queryText: String = "",
    val featuredCollections: List<Collection> = emptyList(),
    val initialFeaturedCollections: List<Collection> = emptyList(),
    val selectedFeaturedCollectionId: String = "",
)