package com.example.pexelsproject.domain.usecase.network

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkGetPhotoByIdUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    suspend fun invoke(id: Int): Photo? {
        return networkRepository.getPhotoById(id)
    }

}