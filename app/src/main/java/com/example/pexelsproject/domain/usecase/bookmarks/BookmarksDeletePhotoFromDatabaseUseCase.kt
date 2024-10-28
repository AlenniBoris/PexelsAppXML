package com.example.pexelsproject.domain.usecase.bookmarks

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.BookmarksRepository
import javax.inject.Inject

class BookmarksDeletePhotoFromDatabaseUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
){

    suspend fun invoke(photoToDelete: Photo){
        bookmarksRepository.deletePhotoFromBookmarksDatabase(photoToDelete)
    }

}