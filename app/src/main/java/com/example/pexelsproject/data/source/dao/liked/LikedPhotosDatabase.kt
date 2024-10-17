package com.example.pexelsproject.data.source.dao.liked

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsproject.data.source.dao.liked.model.LikedPhotoEntity

@Database(
    entities = [LikedPhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LikedPhotosDatabase: RoomDatabase() {
    abstract val dao: LikedPhotosDao
}