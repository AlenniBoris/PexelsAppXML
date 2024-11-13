package com.example.pexelsproject.di

import android.app.Application
import androidx.room.Room
import com.example.pexelsproject.BuildConfig
import com.example.pexelsproject.data.source.api.PhotoAPIService
import com.example.pexelsproject.data.source.dao.bookmarks.BookmarksDatabase
import com.example.pexelsproject.data.source.dao.cache.CacheCollectionsDatabase
import com.example.pexelsproject.data.source.dao.cache.CachePhotosDatabase
import com.example.pexelsproject.data.source.dao.liked.LikedPhotosDatabase
import com.example.pexelsproject.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Injector {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val BOOKMARKS_DATABASE_FILE = "bookmarks-data.db"
    private const val LIKED_DATABASE_FILE = "liked-data.db"
    private const val CACHE_PHOTOS_DATABASE_FILE = "cache-photos-data.db"
    private const val CACHE_COLLECTIONS_DATABASE_FILE = "cache-collections-data.db"

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(HEADER_AUTHORIZATION, BuildConfig.API_KEY)
                .build()

            chain.proceed(request)
        }
        .addNetworkInterceptor(loggingInterceptor)
        .build()


    @Provides
    @Singleton
    fun providePhotoApiService(okHttpClient: OkHttpClient): PhotoAPIService =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PhotoAPIService::class.java)

    @Provides
    @Singleton
    fun provideBookmarksDatabase(application: Application): BookmarksDatabase =
        Room.databaseBuilder(
            application,
            BookmarksDatabase::class.java,
            BOOKMARKS_DATABASE_FILE
        ).build()

    @Provides
    @Singleton
    fun provideLikedPhotosDatabase(application: Application): LikedPhotosDatabase =
        Room.databaseBuilder(
            application,
            LikedPhotosDatabase::class.java,
            LIKED_DATABASE_FILE
        ).build()

    @Provides
    @Singleton
    fun provideCachePhotosDatabase(application: Application): CachePhotosDatabase =
        Room.databaseBuilder(
            application,
            CachePhotosDatabase::class.java,
            CACHE_PHOTOS_DATABASE_FILE
        ).build()

    @Provides
    @Singleton
    fun provideCacheCollectionsDatabase(application: Application): CacheCollectionsDatabase =
        Room.databaseBuilder(
            application,
            CacheCollectionsDatabase::class.java,
            CACHE_COLLECTIONS_DATABASE_FILE
        ).build()

}