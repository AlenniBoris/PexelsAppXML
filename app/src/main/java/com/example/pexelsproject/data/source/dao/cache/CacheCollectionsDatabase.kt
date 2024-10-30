package com.example.pexelsproject.data.source.dao.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsproject.data.source.dao.CollectionsDatabaseDao
import com.example.pexelsproject.data.source.dao.PhotoDatabaseDao
import com.example.pexelsproject.data.source.dao.model.CollectionEntity


@Database(
    entities = [CollectionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CacheCollectionsDatabase: RoomDatabase() {
    abstract val dao: CollectionsDatabaseDao
}