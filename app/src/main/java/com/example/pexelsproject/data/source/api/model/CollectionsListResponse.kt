package com.example.pexelsproject.data.source.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CollectionsListResponse(
    val id: String,
    val title: String,
    val description: String?,
    @SerializedName("private")
    val isPrivate: Boolean,
    @SerializedName("media_count")
    val mediaCount: Int,
    @SerializedName("photos_count")
    val photosCount: Int,
    @SerializedName("videos_count")
    val videosCount: Int
) : Serializable