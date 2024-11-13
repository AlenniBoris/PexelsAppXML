package com.example.pexelsproject.domain.usecase.network

import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.repository.NetworkRepository
import com.example.pexelsproject.utils.Constants
import javax.inject.Inject

class NetworkGetFeaturedCollectionUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    suspend fun invoke(): List<Collection> {
        return networkRepository.getFeaturedCollectionsList(
            Constants.PER_PAGE,
            Constants.NUMBER_FEATURED
        )
    }

}