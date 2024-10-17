package com.example.pexelsproject.data.source.dao.liked

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsproject.data.source.dao.liked.model.LikedPhotoEntity

@Dao
interface LikedPhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotoToLikedDatabase(photoEntity: LikedPhotoEntity)

    @Delete
    suspend fun deletePhotoFromLikedDatabase(photoEntity: LikedPhotoEntity)

    @Query("SELECT * FROM `liked_photo`")
    suspend fun getAllPhotosFromLikedDatabase(): List<LikedPhotoEntity>

    @Query("SELECT * FROM `liked_photo` WHERE id=:id")
    suspend fun getPhotoFromLikedDatabaseById(id: Int): LikedPhotoEntity

    @Query("SELECT COUNT(*) FROM `liked_photo` WHERE id=:id")
    suspend fun countById(id: Int): Int

}