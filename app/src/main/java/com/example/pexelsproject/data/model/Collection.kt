package com.example.pexelsproject.data.model

data class Collection(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)