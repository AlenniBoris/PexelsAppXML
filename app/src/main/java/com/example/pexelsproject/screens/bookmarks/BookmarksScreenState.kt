package com.example.pexelsproject.screens.bookmarks

import com.example.pexelsproject.data.model.Photo

data class BookmarksScreenState(
    val favouritePhotos: List<Photo> = emptyList(),
    val isNoFavourite: Boolean = false
)
