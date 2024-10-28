package com.example.pexelsproject.presentation.liked

import com.example.pexelsproject.domain.model.Photo

data class LikedPhotosState(
    val photos: List<Photo> = emptyList(),
    val isNoLiked: Boolean = false
)