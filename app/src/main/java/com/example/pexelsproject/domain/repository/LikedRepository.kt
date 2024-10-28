package com.example.pexelsproject.domain.repository

import com.example.pexelsproject.domain.model.Photo

interface LikedRepository {

    suspend fun addPhotoToLikedDatabase(photoToAdd: Photo)

    suspend fun deletePhotoFromLikedDatabase(photoToDelete: Photo)

    suspend fun getAllPhotosFromLikedDatabase(): List<Photo>

    suspend fun countById(id: Int): Int

}