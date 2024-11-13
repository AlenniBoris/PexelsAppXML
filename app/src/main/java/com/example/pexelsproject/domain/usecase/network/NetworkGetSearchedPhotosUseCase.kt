package com.example.pexelsproject.domain.usecase.network

import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.repository.NetworkRepository
import com.example.pexelsproject.utils.Constants
import javax.inject.Inject

class NetworkGetSearchedPhotosUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    suspend fun invoke(query: String): List<Photo> {
        return networkRepository.getSearchedPhotosList(
            query,
            Constants.NUMBER_CURATED,
            Constants.PER_PAGE
        )
    }

}