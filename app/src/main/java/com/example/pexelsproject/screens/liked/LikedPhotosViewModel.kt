package com.example.pexelsproject.screens.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.repository.LikedPhotosFromDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LikedPhotosViewModel @Inject constructor(
    private val likedPhotosFromDatabaseRepository: LikedPhotosFromDatabaseRepository
) : ViewModel() {

    val screenState = MutableStateFlow(LikedPhotosState())

    init {
        viewModelScope.launch {
            getLikedPhotos()
        }
    }

    suspend fun getLikedPhotos(){
        val likedPhotosList = likedPhotosFromDatabaseRepository.getAllPhotosFromLikedDatabase()
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
        likedPhotosFromDatabaseRepository.deletePhotoFromLikedDatabase(photo)
    }
}