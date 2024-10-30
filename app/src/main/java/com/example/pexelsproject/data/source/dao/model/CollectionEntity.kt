package com.example.pexelsproject.data.source.dao.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("collection_database")
data class CollectionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String?,
    @ColumnInfo("private")
    val isPrivate: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)