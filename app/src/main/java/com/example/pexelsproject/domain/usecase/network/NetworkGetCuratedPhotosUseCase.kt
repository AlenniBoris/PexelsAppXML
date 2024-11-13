package com.example.pexelsproject.domain.usecase.network

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.NetworkRepository
import com.example.pexelsproject.utils.Constants
import javax.inject.Inject

class NetworkGetCuratedPhotosUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    suspend fun invoke(): List<Photo> {
        return networkRepository.getCuratedPhotosList(Constants.NUMBER_CURATED, Constants.PER_PAGE)
    }

}