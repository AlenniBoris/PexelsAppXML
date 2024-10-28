package com.example.pexelsproject.di

import com.example.pexelsproject.data.repository.LikedRepositoryImpl
import com.example.pexelsproject.data.repository.BookmarksRepositoryImpl
import com.example.pexelsproject.data.repository.NetworkRepositoryImpl
import com.example.pexelsproject.domain.repository.LikedRepository
import com.example.pexelsproject.domain.repository.BookmarksRepository
import com.example.pexelsproject.domain.repository.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule{

    @Binds
    fun bindsLikedPhotosFromDatabaseRepository(
        likedRepositoryImpl: LikedRepositoryImpl
    ) : LikedRepository

    @Binds
    fun bindsPhotosFromDatabaseRepository(
        bookmarksRepositoryImpl: BookmarksRepositoryImpl
    ) : BookmarksRepository

    @Binds
    fun bindsPhotosFromNetworkRepository(
        networkRepositoryImpl: NetworkRepositoryImpl
    ) : NetworkRepository

}