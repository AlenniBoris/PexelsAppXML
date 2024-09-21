package com.example.pexelsproject.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsproject.data.source.dao.model.PhotoEntity

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoEntity: PhotoEntity)

    @Delete
    suspend fun deletePhoto(photoEntity: PhotoEntity)

    @Query("SELECT * FROM `bookmarks_photo`")
    suspend fun getAllFavourites(): List<PhotoEntity>

    @Query("SELECT * FROM `bookmarks_photo` WHERE id=:id")
    suspend fun getFavouriteById(id: Int): PhotoEntity

    @Query("SELECT COUNT(*) FROM `bookmarks_photo` WHERE id=:id")
    suspend fun countById(id: Int): Int

}