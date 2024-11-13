package com.example.pexelsproject.domain.usecase.bookmarks

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.BookmarksRepository
import javax.inject.Inject

class BookmarksGetAllPhotosFromDatabaseUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) {

    suspend fun invoke(): List<Photo> {
        return bookmarksRepository.getAllPhotosFromBookmarksDatabase()
    }

}