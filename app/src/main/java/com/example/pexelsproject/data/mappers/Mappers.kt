package com.example.pexelsproject.data.mappers

import com.example.pexelsproject.data.model.CollectionsList
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.model.PhotoFeatures
import com.example.pexelsproject.data.source.api.model.CollectionsListResponse
import com.example.pexelsproject.data.source.api.model.PhotoResponse

fun CollectionsListResponse.asCollections(): CollectionsList {
    return CollectionsList(
        id = this.id,
        title = this.title,
        description = this.description,
        private = this.private,
        mediaCount = this.mediaCount,
        photosCount = this.photosCount,
        videosCount = this.videosCount
    )
}

fun PhotoResponse.asPhoto() : Photo {
    return Photo(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = PhotoFeatures(
            original = this.src.original,
            large2x = this.src.large2x,
            large = this.src.large,
            medium = this.src.medium,
            small = this.src.small,
            portrait = this.src.portrait,
            landscape = this.src.landscape,
            tiny = this.src.tiny
        ),
        liked = this.liked,
        alt = this.alt
    )
}
