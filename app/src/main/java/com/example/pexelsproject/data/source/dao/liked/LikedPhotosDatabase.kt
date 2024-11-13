package com.example.pexelsproject.data.source.dao.liked

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsproject.data.source.dao.PhotoDatabaseDao
import com.example.pexelsproject.data.source.dao.model.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LikedPhotosDatabase : RoomDatabase() {
    abstract val dao: PhotoDatabaseDao
}