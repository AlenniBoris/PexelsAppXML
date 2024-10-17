package com.example.pexelsproject.data.repository

import android.util.Log
import com.example.pexelsproject.data.mappers.asPhoto
import com.example.pexelsproject.data.mappers.asPhotoEntity
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.source.dao.bookmarks.BookmarksDatabase

class PhotosFromDatabaseRepository (
    private val database: BookmarksDatabase
) {
    suspend fun addPhotoToBookmarksDatabase(photoToAdd: Photo) {
        try {
            database.dao.addPhotoToBookmarksDatabase(photoToAdd.asPhotoEntity())
        }catch (e: Exception){
            Log.e("BookmarksDatabase-addPhoto", e.message.toString())
        }
    }

    suspend fun deletePhotoFromBookmarksDatabase(photoToDelete: Photo) {
        try {
            database.dao.deletePhotoFromBookmarksDatabase(photoToDelete.asPhotoEntity())
        }catch (e: Exception){
            Log.e("BookmarksDatabase-deletePhoto", e.message.toString())
        }
    }

    suspend fun getAllPhotosFromBookmarksDatabase(): List<Photo> {
        val photos = try {
            database.dao.getAllPhotosFromBookmarksDatabase().map { it.asPhoto() }
        } catch (e: Exception){
            Log.e("BookmarksDatabase-getAllFavourites", e.message.toString())
            emptyList()
        }
        return photos
    }

    suspend fun getPhotoFromBookmarksDatabaseById(id: Int): Photo? {
        val photo = try {
            database.dao.getPhotoFromBookmarksDatabaseById(id).asPhoto()
        }catch (e: Exception){
            Log.e("BookmarksDatabase-getFavouriteById", e.message.toString())
            null
        }

        return photo
    }

    suspend fun countById(id: Int): Int {
        val count = try {
            database.dao.countById(id)
        } catch (e: Exception){
            Log.e("BookmarksDatabase-countById", e.message.toString())
            0
        }
        return count
    }
}