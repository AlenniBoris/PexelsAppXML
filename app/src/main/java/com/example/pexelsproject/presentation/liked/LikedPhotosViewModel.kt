package com.example.pexelsproject.presentation.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.data.repository.LikedRepositoryImpl
import com.example.pexelsproject.domain.usecase.liked.LikedDeletePhotoFromLikedDatabase
import com.example.pexelsproject.domain.usecase.liked.LikedGetAllPhotosFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LikedPhotosViewModel @Inject constructor(
    private val likedDeletePhotoFromLikedDatabaseUseCase: LikedDeletePhotoFromLikedDatabase,
    private val likedGetAllPhotosFromDatabaseUseCase: LikedGetAllPhotosFromDatabaseUseCase
) : ViewModel() {

    val screenState = MutableStateFlow(LikedPhotosState())

    init {
        viewModelScope.launch {
            getLikedPhotos()
        }
    }

    suspend fun getLikedPhotos(){
        val likedPhotosList = likedGetAllPhotosFromDatabaseUseCase.invoke()
        screenState.update{ state ->
            state.copy(
                photos = likedPhotosList,
                isNoLiked = likedPhotosList.isEmpty()
            )
        }
    }

    fun deletePhotoFromLiked(photo: Photo){
        viewModelScope.launch {
            deletePhotoFromLikedInit(photo)
            getLikedPhotos()
        }
    }

    suspend fun deletePhotoFromLikedInit(photo: Photo){
        likedDeletePhotoFromLikedDatabaseUseCase.invoke(photo)
    }
}