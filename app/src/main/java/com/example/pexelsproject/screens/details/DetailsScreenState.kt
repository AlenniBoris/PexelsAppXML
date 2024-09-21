package com.example.pexelsproject.screens.details

import com.example.pexelsproject.data.model.Photo

data class DetailsScreenState(
    val currentPhoto: Photo? = null,
    val photoIsFavourite: Boolean = false
)
