package com.example.pexelsproject.data.source.api

import com.example.pexelsproject.data.source.api.model.FeaturedCollectionsResponse
import com.example.pexelsproject.data.source.api.model.PhotoResponse
import com.example.pexelsproject.data.source.api.model.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoAPIService {

    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: Int
    ): PhotoResponse

    @GET("curated")
    suspend fun getCuratedPhotos(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PhotosResponse

    @GET("search")
    suspend fun getSearchedPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PhotosResponse

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): FeaturedCollectionsResponse
}