package com.example.pexelsproject.domain.liked

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.model.PhotoFeatures
import com.example.pexelsproject.domain.repository.LikedRepository
import com.example.pexelsproject.domain.usecase.liked.LikedAddPhotoToDatabaseUseCase
import com.example.pexelsproject.domain.usecase.liked.LikedCountByIdUseCase
import com.example.pexelsproject.domain.usecase.liked.LikedDeletePhotoFromLikedDatabaseUseCase
import com.example.pexelsproject.domain.usecase.liked.LikedGetAllPhotosFromDatabaseUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LikedUseCasesTests {

    private lateinit var addPhotoToDatabaseUseCase: LikedAddPhotoToDatabaseUseCase
    private lateinit var countByIdUseCase: LikedCountByIdUseCase
    private lateinit var deletePhotoFromLikedDatabaseUseCase: LikedDeletePhotoFromLikedDatabaseUseCase
    private lateinit var getAllPhotosFromDatabaseUseCase: LikedGetAllPhotosFromDatabaseUseCase
    private val likedRepository: LikedRepository = mockk(relaxed = true)

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
    fun setUp() {
        addPhotoToDatabaseUseCase = LikedAddPhotoToDatabaseUseCase(likedRepository)
        countByIdUseCase = LikedCountByIdUseCase(likedRepository)
        deletePhotoFromLikedDatabaseUseCase =
            LikedDeletePhotoFromLikedDatabaseUseCase(likedRepository)
        getAllPhotosFromDatabaseUseCase = LikedGetAllPhotosFromDatabaseUseCase(likedRepository)
    }

    @Test
    fun `Data saving function is calling`() = runTest {

        coEvery { likedRepository.addPhotoToLikedDatabase(first) } returns Unit

        addPhotoToDatabaseUseCase.invoke(first)

        coVerify(exactly = 1) { likedRepository.addPhotoToLikedDatabase(first) }

    }

    @Test
    fun `count by id is working`() = runTest {

        addPhotoToDatabaseUseCase.invoke(first)

        coEvery { likedRepository.addPhotoToLikedDatabase(first) } returns Unit

        coEvery { likedRepository.countById(first.id) } returns 1

        val result = countByIdUseCase.invoke(first.id)

        assertEquals(1, result)

    }


    @Test
    fun `get all data is working`() = runTest {

        addPhotoToDatabaseUseCase.invoke(first)
        addPhotoToDatabaseUseCase.invoke(second)

        coEvery { likedRepository.addPhotoToLikedDatabase(first) } returns Unit
        coEvery { likedRepository.addPhotoToLikedDatabase(second) } returns Unit

        coEvery { likedRepository.getAllPhotosFromLikedDatabase() } returns listOf(first, second)

        val result = getAllPhotosFromDatabaseUseCase.invoke().size

        assertEquals(2, result)

    }

    @Test
    fun `deleting data is working`() = runTest {

        addPhotoToDatabaseUseCase.invoke(first)
        addPhotoToDatabaseUseCase.invoke(second)

        coEvery { likedRepository.getAllPhotosFromLikedDatabase() } returns listOf(first, second)

        val beforeSize = getAllPhotosFromDatabaseUseCase.invoke().size

        assertEquals(2, beforeSize)

        coEvery { likedRepository.deletePhotoFromLikedDatabase(first) } returns Unit
        coEvery { likedRepository.deletePhotoFromLikedDatabase(second) } returns Unit

        deletePhotoFromLikedDatabaseUseCase.invoke(first)
        deletePhotoFromLikedDatabaseUseCase.invoke(second)

        coEvery { likedRepository.getAllPhotosFromLikedDatabase() } returns emptyList()

        val afterSize = getAllPhotosFromDatabaseUseCase.invoke().size

        assertEquals(0, afterSize)
    }

}