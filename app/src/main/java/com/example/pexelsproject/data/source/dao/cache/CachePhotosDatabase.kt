package com.example.pexelsproject.data.source.dao.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsproject.data.source.dao.PhotoDatabaseDao
import com.example.pexelsproject.data.source.dao.model.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CachePhotosDatabase: RoomDatabase() {
    abstract val dao: PhotoDatabaseDao
}