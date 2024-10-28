package com.example.pexelsproject.presentation.bookmarks

import com.example.pexelsproject.domain.model.Photo

data class BookmarksScreenState(
    val favouritePhotos: List<Photo> = emptyList(),
    val isNoFavourite: Boolean = false
)
