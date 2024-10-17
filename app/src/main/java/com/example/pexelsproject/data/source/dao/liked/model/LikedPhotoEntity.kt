package com.example.pexelsproject.data.source.dao.liked.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("liked_photo")
data class LikedPhotoEntity(
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
    val src: LikedPhotoSrcListEntity,
    val liked: Boolean,
    val alt: String
)