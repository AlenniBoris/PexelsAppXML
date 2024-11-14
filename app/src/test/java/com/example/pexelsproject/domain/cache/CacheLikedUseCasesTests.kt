package com.example.pexelsproject.domain.cache

import android.content.Context
import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.model.PhotoFeatures
import com.example.pexelsproject.domain.repository.CacheCollectionsRepository
import com.example.pexelsproject.domain.repository.CachePhotosRepository
import com.example.pexelsproject.domain.usecase.cache.CacheClearDataUseCase
import com.example.pexelsproject.domain.usecase.cache.CacheGetAllDataUseCase
import com.example.pexelsproject.domain.usecase.cache.CacheSaveDataUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class CacheLikedUseCasesTests {

    private val cachePhotosRepository: CachePhotosRepository = mockk(relaxed = true)
    private val cacheCollectionsRepository: CacheCollectionsRepository = mockk(relaxed = true)

    private lateinit var cacheClearDataUseCase: CacheClearDataUseCase
    private lateinit var cacheSaveDataUseCase: CacheSaveDataUseCase
    private lateinit var cacheGetAllDataUseCase: CacheGetAllDataUseCase

    private lateinit var context: Context

    private val firstPhoto = Photo(
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

    private val firstCollection = Collection(
        id = "1",
        title = "1",
        description = "1",
        isPrivate = true,
        mediaCount = 1,
        photosCount = 1,
        videosCount = 1
    )


    @Before
    fun setUp() {
        context = mock(Context::class.java)

        cacheClearDataUseCase = CacheClearDataUseCase(
            cachePhotosRepository, cacheCollectionsRepository, context
        )

        cacheSaveDataUseCase = CacheSaveDataUseCase(
            cachePhotosRepository, cacheCollectionsRepository
        )

        cacheGetAllDataUseCase = CacheGetAllDataUseCase(
            cachePhotosRepository, cacheCollectionsRepository
        )
    }

    @Test
    fun `Data saving function is calling`() = runTest {

        coEvery { cachePhotosRepository.addPhotosToCacheDatabase(listOf(firstPhoto)) } returns Unit

        cacheSaveDataUseCase.invokeSavePhotos(listOf(firstPhoto))

        coVerify(exactly = 1) { cachePhotosRepository.addPhotosToCacheDatabase(listOf(firstPhoto)) }

        coEvery { cacheCollectionsRepository.addCollectionsToCacheDatabase(listOf(firstCollection)) } returns Unit

        cacheSaveDataUseCase.invokeSaveCollections(listOf(firstCollection))

        coVerify(exactly = 1) {
            cacheCollectionsRepository.addCollectionsToCacheDatabase(
                listOf(
                    firstCollection
                )
            )
        }

    }

    @Test
    fun `get all data is working`() = runTest {

        cacheSaveDataUseCase.invokeSavePhotos(listOf(firstPhoto))
        cacheSaveDataUseCase.invokeSaveCollections(listOf(firstCollection))

        coEvery { cachePhotosRepository.addPhotosToCacheDatabase(listOf(firstPhoto)) } returns Unit
        coEvery { cacheCollectionsRepository.addCollectionsToCacheDatabase(listOf(firstCollection)) } returns Unit

        coEvery { cachePhotosRepository.getAllPhotosFromCacheDatabase() } returns listOf(firstPhoto)
        coEvery { cacheCollectionsRepository.getAllCollectionsFromCacheDatabase() } returns listOf(
            firstCollection
        )

        val (photosResult, collectionsResult) = cacheGetAllDataUseCase.invoke()

        assertEquals(1, photosResult.size)
        assertEquals(1, collectionsResult.size)

    }

    @Test
    fun `deleting data is working`() = runTest {

        cacheSaveDataUseCase.invokeSavePhotos(listOf(firstPhoto))
        cacheSaveDataUseCase.invokeSaveCollections(listOf(firstCollection))

        coEvery { cachePhotosRepository.getAllPhotosFromCacheDatabase() } returns listOf(firstPhoto)
        coEvery { cacheCollectionsRepository.getAllCollectionsFromCacheDatabase() } returns listOf(
            firstCollection
        )

        val (beforePhotos, beforeCollections) = cacheGetAllDataUseCase.invoke()

        assertEquals(1, beforePhotos.size)
        assertEquals(1, beforeCollections.size)

        coEvery { cachePhotosRepository.deletePhotosFromCacheDatabase() } returns Unit
        coEvery { cacheCollectionsRepository.deleteCollectionsFromCacheDatabase() } returns Unit

        cacheClearDataUseCase.invokeDeletePhotos()
        cacheClearDataUseCase.invokeDeleteCollections()

        coEvery { cachePhotosRepository.getAllPhotosFromCacheDatabase() } returns emptyList()
        coEvery { cacheCollectionsRepository.getAllCollectionsFromCacheDatabase() } returns emptyList()

        val (afterPhotos, afterCollections) = cacheGetAllDataUseCase.invoke()

        assertEquals(0, afterPhotos.size)
        assertEquals(0, afterCollections.size)

    }

}