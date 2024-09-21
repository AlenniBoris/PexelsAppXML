package com.example.pexelsproject.data.source.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsproject.data.source.dao.model.PhotoEntity

@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarksDatabase: RoomDatabase() {
    abstract val dao: BookmarksDao
}