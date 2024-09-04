package com.example.pexelsproject.data.source.api.model

import com.example.pexelsproject.data.source.api.model.PhotoResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PhotosResponse(
    @SerializedName("total_results")
    val totalResults: Int,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<PhotoResponse>,
    @SerializedName("next_page")
    val nextPage: String?
) : Serializable