package com.example.pexelsproject.data.source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsproject.data.source.dao.model.PhotoEntity

@Dao
interface PhotoDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotoToDatabase(photoEntity: PhotoEntity)

    @Delete
    suspend fun deletePhotoFromDatabase(photoEntity: PhotoEntity)

    @Query("SELECT * FROM `database_photos`")
    suspend fun getAllPhotosFromDatabase(): List<PhotoEntity>

    @Query("SELECT * FROM `database_photos` WHERE id=:id")
    suspend fun getPhotoFromDatabaseById(id: Int): PhotoEntity

    @Query("SELECT COUNT(*) FROM `database_photos` WHERE id=:id")
    suspend fun countById(id: Int): Int

    @Query("DELETE FROM `database_photos`")
    suspend fun clearTable()

}