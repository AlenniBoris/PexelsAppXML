package com.example.pexelsproject.domain.bookmarks

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.model.PhotoFeatures
import com.example.pexelsproject.domain.repository.BookmarksRepository
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksAddPhotoToDatabaseUseCase
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksCountByIdUseCase
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksGetAllPhotosFromDatabaseUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class GetAllPhotosFromDatabaseUseCaseTest {

    private lateinit var addPhotoToDatabaseUseCase: BookmarksAddPhotoToDatabaseUseCase
    private lateinit var getAllPhotosFromDatabaseUseCase: BookmarksGetAllPhotosFromDatabaseUseCase
    private lateinit var countByIdUseCase: BookmarksCountByIdUseCase
    private val bookmarksRepository: BookmarksRepository = mockk(relaxed = true)

    private val first = Photo(
        id = 1,
        width = 1,
        height = 1,
        url = "1",
        photographer = "1",
        photographerUrl = "1",
        photographerId = 1,
        avgColor = "1",
        src = PhotoFeatures(
            original = "1",
            large2x = "1",
            large = "1",
            medium = "1",
            small = "1",
            portrait = "1",
            landscape = "1",
            tiny = "1"
        ),
        liked = true,
        alt = "1"
    )
    private val second = Photo(
        id = 2,
        width = 1,
        height = 1,
        url = "1",
        photographer = "1",
        photographerUrl = "1",
        photographerId = 1,
        avgColor = "1",
        src = PhotoFeatures(
            original = "1",
            large2x = "1",
            large = "1",
            medium = "1",
            small = "1",
            portrait = "1",
            landscape = "1",
            tiny = "1"
        ),
        liked = true,
        alt = "1"
    )

    @Before
    fun setUp(){
        addPhotoToDatabaseUseCase = BookmarksAddPhotoToDatabaseUseCase(bookmarksRepository)
        getAllPhotosFromDatabaseUseCase = BookmarksGetAllPhotosFromDatabaseUseCase(bookmarksRepository)
        countByIdUseCase = BookmarksCountByIdUseCase(bookmarksRepository)
    }

    @Test
    fun `should call add function on repository`() = runTest{

        addPhotoToDatabaseUseCase.invoke(first)
        addPhotoToDatabaseUseCase.invoke(second)

        coEvery { bookmarksRepository.getAllPhotosFromBookmarksDatabase() } returns listOf(first,second)

        val result = bookmarksRepository.getAllPhotosFromBookmarksDatabase()

        coVerify (exactly = 1) { bookmarksRepository.getAllPhotosFromBookmarksDatabase() }

    }

    @Test
    fun `should give all photos from database`() = runTest {

        addPhotoToDatabaseUseCase.invoke(first)
        addPhotoToDatabaseUseCase.invoke(second)

        coEvery { bookmarksRepository.getAllPhotosFromBookmarksDatabase() } returns listOf(first, second)

        val resultLength = bookmarksRepository.getAllPhotosFromBookmarksDatabase().size

        assertEquals(2, resultLength)

    }

}