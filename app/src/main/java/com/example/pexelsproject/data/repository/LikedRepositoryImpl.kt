package com.example.pexelsproject.data.repository

import android.util.Log
import com.example.pexelsproject.data.mappers.asPhoto
import com.example.pexelsproject.data.mappers.asPhotoEntity
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.data.source.dao.liked.LikedPhotosDatabase
import com.example.pexelsproject.domain.repository.LikedRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LikedRepositoryImpl @Inject constructor(
    private val database: LikedPhotosDatabase
): LikedRepository {

    override suspend fun addPhotoToLikedDatabase(photoToAdd: Photo) {
        try {
            database.dao.addPhotoToDatabase(photoToAdd.asPhotoEntity())
        }catch (e: Exception){
            Log.e("LikedDatabase-addPhoto", e.message.toString())
        }
    }

    override suspend fun deletePhotoFromLikedDatabase(photoToDelete: Photo) {
        try {
            database.dao.deletePhotoFromDatabase(photoToDelete.asPhotoEntity())
        }catch (e: Exception){
            Log.e("LikedDatabase-deletePhoto", e.message.toString())
        }
    }

    override suspend fun getAllPhotosFromLikedDatabase(): List<Photo> {
        val photos = try {
            database.dao.getAllPhotosFromDatabase().map { it.asPhoto() }
        } catch (e: Exception){
            Log.e("LikedDatabase-getAllFavourites", e.message.toString())
            emptyList()
        }
        return photos
    }

    override suspend fun countById(id: Int): Int {
        val count = try {
            database.dao.countById(id)
        } catch (e: Exception){
            Log.e("LikedDatabase-countById", e.message.toString())
            0
        }
        return count
    }

}