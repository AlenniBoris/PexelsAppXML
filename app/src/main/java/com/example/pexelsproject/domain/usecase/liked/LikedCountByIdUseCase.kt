package com.example.pexelsproject.domain.usecase.liked

import com.example.pexelsproject.domain.repository.LikedRepository
import javax.inject.Inject

class LikedCountByIdUseCase @Inject constructor(
    private val likedRepository: LikedRepository
) {

    suspend fun invoke(id: Int): Int {
        return likedRepository.countById(id)
    }

}