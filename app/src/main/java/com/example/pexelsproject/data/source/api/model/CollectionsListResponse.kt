package com.example.pexelsproject.data.source.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CollectionsListResponse(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    @SerializedName("media_count")
    val mediaCount: Int,
    @SerializedName("photos_count")
    val photosCount: Int,
    @SerializedName("videos_count")
    val videosCount: Int
) : Serializable