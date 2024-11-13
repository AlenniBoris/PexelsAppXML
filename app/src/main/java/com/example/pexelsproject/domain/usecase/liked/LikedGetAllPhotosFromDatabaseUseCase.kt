package com.example.pexelsproject.domain.usecase.liked

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.LikedRepository
import javax.inject.Inject

class LikedGetAllPhotosFromDatabaseUseCase @Inject constructor(
    private val likedRepository: LikedRepository
) {

    suspend fun invoke(): List<Photo> {
        return likedRepository.getAllPhotosFromLikedDatabase()
    }

}