package com.example.pexelsproject.data.mappers

import com.example.pexelsproject.data.source.api.model.CollectionsListResponse
import com.example.pexelsproject.data.source.api.model.PhotoResponse
import com.example.pexelsproject.data.source.dao.model.CollectionEntity
import com.example.pexelsproject.data.source.dao.model.PhotoEntity
import com.example.pexelsproject.data.source.dao.model.PhotoSrcListEntity
import com.example.pexelsproject.domain.model.Collection
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.model.PhotoFeatures

fun CollectionsListResponse.asCollections(): Collection {
    return Collection(
        id = id,
        title = title,
        description = description,
        isPrivate = isPrivate,
        mediaCount = mediaCount,
        photosCount = photosCount,
        videosCount = videosCount
    )
}

fun PhotoResponse.asPhoto(): Photo {
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

fun Photo.asPhotoEntity(): PhotoEntity {
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

fun Collection.asCollectionEntity(): CollectionEntity {
    return CollectionEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isPrivate = this.isPrivate,
        mediaCount = this.mediaCount,
        photosCount = this.photosCount,
        videosCount = this.videosCount
    )
}

fun CollectionEntity.asCollection(): Collection {
    return Collection(
        id = this.id,
        title = this.title,
        description = this.description,
        isPrivate = this.isPrivate,
        mediaCount = this.mediaCount,
        photosCount = this.photosCount,
        videosCount = this.videosCount
    )
}