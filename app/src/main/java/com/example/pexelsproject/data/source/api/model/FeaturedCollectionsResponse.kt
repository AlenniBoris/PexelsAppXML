package com.example.pexelsproject.data.source.api.model

import com.example.pexelsproject.data.source.api.model.CollectionsListResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FeaturedCollectionsResponse(
    val collections: List<CollectionsListResponse>,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("prev_page")
    val prevPage: String,
    @SerializedName("next_page")
    val nextPage: String
) : Serializable