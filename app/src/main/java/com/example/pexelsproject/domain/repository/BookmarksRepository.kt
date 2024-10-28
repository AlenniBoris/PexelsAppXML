package com.example.pexelsproject.domain.repository

import com.example.pexelsproject.domain.model.Photo

interface BookmarksRepository {

    suspend fun addPhotoToBookmarksDatabase(photoToAdd: Photo)

    suspend fun deletePhotoFromBookmarksDatabase(photoToDelete: Photo)

    suspend fun getAllPhotosFromBookmarksDatabase(): List<Photo>

    suspend fun getPhotoFromBookmarksDatabaseById(id: Int): Photo?

    suspend fun countById(id: Int): Int

}