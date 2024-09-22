package com.example.pexelsproject.di

import android.app.Application
import androidx.room.Room
import com.example.pexelsproject.data.repository.PhotosFromDatabaseRepository
import com.example.pexelsproject.data.repository.PhotosFromNetworkRepository
import com.example.pexelsproject.data.source.api.PhotoAPIService
import com.example.pexelsproject.data.source.dao.BookmarksDatabase
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
object Injector{
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val DATABASE_FILE = "bookmarks-data.db"

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
                .addHeader(HEADER_AUTHORIZATION, Constants.API_KEY)
                .build()

            chain.proceed(request)
        }
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providePhotoApiService(okHttpClient: OkHttpClient) : PhotoAPIService =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PhotoAPIService::class.java)

    @Provides
    @Singleton
    fun providePhotosFromNetworkRepository(photoApiService: PhotoAPIService): PhotosFromNetworkRepository =
        PhotosFromNetworkRepository(photoApiService)
    @Provides
    @Singleton
    fun provideBookmarksDatabase(application: Application): BookmarksDatabase =
        Room.databaseBuilder(
            application,
            BookmarksDatabase::class.java,
            DATABASE_FILE
        ).build()

    @Provides
    @Singleton
    fun providesBookmarksRepository(database: BookmarksDatabase): PhotosFromDatabaseRepository =
        PhotosFromDatabaseRepository(database)

}