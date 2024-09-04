package com.example.pexelsproject.screens.main

import com.example.pexelsproject.data.model.CollectionsList
import com.example.pexelsproject.data.model.Photo
import java.util.Collections

data class MainScreenState(
    val photos: List<Photo> = emptyList(),
    val errorState: Boolean = false,
    val isActive: Boolean = false,
    val history: Set<String> = emptySet(),
    val queryText: String = "",
    val featuredCollections: List<CollectionsList> = emptyList(),
    val initialFeaturedCollections: List<CollectionsList> = emptyList(),
    val selectedFeaturedCollectionId: String = "",
)