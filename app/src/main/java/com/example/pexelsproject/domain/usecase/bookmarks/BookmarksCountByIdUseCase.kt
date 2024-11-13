package com.example.pexelsproject.domain.usecase.bookmarks

import com.example.pexelsproject.domain.repository.BookmarksRepository
import javax.inject.Inject

class BookmarksCountByIdUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) {

    suspend fun invoke(id: Int): Int {
        return bookmarksRepository.countById(id)
    }

}