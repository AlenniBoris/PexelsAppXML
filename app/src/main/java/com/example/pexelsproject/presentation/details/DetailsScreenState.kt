package com.example.pexelsproject.presentation.details

import com.example.pexelsproject.domain.model.Photo

data class DetailsScreenState(
    val currentPhoto: Photo? = null,
    val photoIsFavourite: Boolean = false,
    val photoIsLiked: Boolean = false,
)
