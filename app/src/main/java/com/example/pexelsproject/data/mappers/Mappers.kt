package com.example.pexelsproject.data.mappers

import com.example.pexelsproject.data.model.Collection
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.model.PhotoFeatures
import com.example.pexelsproject.data.source.api.model.CollectionsListResponse
import com.example.pexelsproject.data.source.api.model.PhotoResponse
import com.example.pexelsproject.data.source.dao.model.PhotoEntity
import com.example.pexelsproject.data.source.dao.model.PhotoSrcListEntity

fun CollectionsListResponse.asCollections(): Collection {
    return Collection(
        id = id,
        title = title,
        description = description,
        private = private,
        mediaCount = mediaCount,
        photosCount = photosCount,
        videosCount = videosCount
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

fun Photo.asPhotoEntity() : PhotoEntity{
    return PhotoEntity(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = PhotoSrcListEntity(
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
        alt = this.alt,
    )
}

fun PhotoEntity.asPhoto(): Photo {
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