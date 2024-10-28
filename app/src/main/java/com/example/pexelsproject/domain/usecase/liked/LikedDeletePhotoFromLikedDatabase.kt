package com.example.pexelsproject.domain.usecase.liked

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.LikedRepository
import javax.inject.Inject

class LikedDeletePhotoFromLikedDatabase @Inject constructor(
    private val likedRepository: LikedRepository
){

    suspend fun invoke(photoToDelete: Photo){
        likedRepository.deletePhotoFromLikedDatabase(photoToDelete)
    }

}