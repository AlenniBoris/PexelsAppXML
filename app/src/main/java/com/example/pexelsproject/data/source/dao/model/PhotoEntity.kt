package com.example.pexelsproject.data.source.dao.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pexelsproject.data.source.api.model.PhotoSrcListResponse
import com.google.gson.annotations.SerializedName

@Entity("bookmarks_photo")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @ColumnInfo("photographer_url")
    val photographerUrl: String,
    @ColumnInfo("photographer_id")
    val photographerId: Int,
    @ColumnInfo("avg_color")
    val avgColor: String,
    @Embedded(prefix = "src_")
    val src: PhotoSrcListEntity,
    val liked: Boolean,
    val alt: String
)