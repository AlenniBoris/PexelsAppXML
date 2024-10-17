package com.example.pexelsproject.screens.liked

import com.example.pexelsproject.data.model.Photo

data class LikedPhotosState(
    val photos: List<Photo> = emptyList(),
    val isNoLiked: Boolean = false
)