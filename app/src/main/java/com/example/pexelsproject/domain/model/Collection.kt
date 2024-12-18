package com.example.pexelsproject.domain.model

data class Collection(
    val id: String,
    val title: String,
    val description: String?,
    val isPrivate: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)