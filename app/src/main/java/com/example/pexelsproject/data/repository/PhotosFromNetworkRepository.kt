package com.example.pexelsproject.data.repository

import android.util.Log
import com.example.pexelsproject.data.mappers.asCollections
import com.example.pexelsproject.data.mappers.asPhoto
import com.example.pexelsproject.data.model.CollectionsList
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.source.api.PhotoAPIService
import javax.inject.Inject

class PhotosFromNetworkRepository @Inject constructor(private val photoApiService: PhotoAPIService) {

    suspend fun getPhotoById(id: Int): Photo?{
        val photo = try {
            photoApiService.getPhotoById(id).asPhoto()
        } catch (e: Exception){
            Log.e("PhotoRepository-getPhotoById", e.message.toString())
            null
        }
        return photo
    }

    suspend fun getCuratedPhotosList(page: Int, perPage: Int): List<Photo>{
        val listOfPhotos = try {
            photoApiService.getCuratedPhotos(page, perPage)
                .photos
                .map { it.asPhoto() }

        } catch (e: Exception){
            Log.e("PhotoRepository-getCuratedPhotosList", e.message.toString())
            emptyList()
        }
        return listOfPhotos
    }

    suspend fun getSearchedPhotosList(query: String, page: Int, perPage: Int): List<Photo>{
        val listOfPhotos = try {
            photoApiService.getSearchedPhotos(query, page, perPage)
                .photos
                .map { it.asPhoto() }

        } catch (e: Exception){
            Log.e("PhotoRepository-getSearchedPhotosList", e.message.toString())
            emptyList()
        }
        return listOfPhotos
    }

    suspend fun getFeaturedCollectionsList(page: Int, perPage: Int): List<CollectionsList>{
        val listOfCollections = try {
            photoApiService.getFeaturedCollections(page, perPage)
                .collections
                .map { it.asCollections() }
        } catch (e: Exception){
            Log.e("PhotoRepository featured-getFeaturedCollectionsList", e.message.toString())
            emptyList()
        }
        return listOfCollections
    }
}