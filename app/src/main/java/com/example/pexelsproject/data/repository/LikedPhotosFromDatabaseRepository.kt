package com.example.pexelsproject.data.repository

import android.util.Log
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsproject.data.mappers.asLikedPhotoEntity
import com.example.pexelsproject.data.mappers.asPhoto
import com.example.pexelsproject.data.mappers.asPhotoEntity
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.source.dao.liked.LikedPhotosDatabase
import com.example.pexelsproject.data.source.dao.liked.model.LikedPhotoEntity

class LikedPhotosFromDatabaseRepository(
    private val database: LikedPhotosDatabase
) {

    suspend fun addPhotoToLikedDatabase(photoToAdd: Photo) {
        try {
            database.dao.addPhotoToLikedDatabase(photoToAdd.asLikedPhotoEntity())
        }catch (e: Exception){
            Log.e("LikedDatabase-addPhoto", e.message.toString())
        }
    }

    suspend fun deletePhotoFromLikedDatabase(photoToDelete: Photo) {
        try {
            database.dao.deletePhotoFromLikedDatabase(photoToDelete.asLikedPhotoEntity())
        }catch (e: Exception){
            Log.e("LikedDatabase-deletePhoto", e.message.toString())
        }
    }

    suspend fun getAllPhotosFromLikedDatabase(): List<Photo> {
        val photos = try {
            database.dao.getAllPhotosFromLikedDatabase().map { it.asPhoto() }
        } catch (e: Exception){
            Log.e("LikedDatabase-getAllFavourites", e.message.toString())
            emptyList()
        }
        return photos
    }

    suspend fun getPhotoFromLikedDatabaseById(id: Int): Photo? {
        val photo = try {
            database.dao.getPhotoFromLikedDatabaseById(id).asPhoto()
        }catch (e: Exception){
            Log.e("LikedDatabase-getFavouriteById", e.message.toString())
            null
        }

        return photo
    }

    suspend fun countById(id: Int): Int {
        val count = try {
            database.dao.countById(id)
        } catch (e: Exception){
            Log.e("LikedDatabase-countById", e.message.toString())
            0
        }
        return count
    }

}