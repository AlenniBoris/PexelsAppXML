package com.example.pexelsproject.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.repository.PhotosFromDatabaseRepository
import com.example.pexelsproject.data.repository.PhotosFromNetworkRepository
import com.example.pexelsproject.utils.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val photoRepository: PhotosFromNetworkRepository,
    private val bookmarksRepository: PhotosFromDatabaseRepository,
    private val connectivityRepository: ConnectivityRepository,
) : ViewModel() {

    val screenState = MutableStateFlow(DetailsScreenState())

    val isOnline = connectivityRepository.isConnected.asLiveData()

    fun checkInternetConnection(){
        viewModelScope.launch {
            connectivityRepository.checkInternetConnection()
        }
    }

    private fun changeIsFavourite(isFavourite: Boolean){
        screenState.update { state -> state.copy(photoIsFavourite = isFavourite) }
    }

    private fun assignPhoto(photo: Photo?){
        screenState.update { state ->
            state.copy(currentPhoto = photo)
        }
    }

    fun getPhotoFromPexelsById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val photo = photoRepository.getPhotoById(id)
                Log.d("PexelsById", photo.toString())
                assignPhoto(photo)
                countPhotosById(id)
            }
        }
    }

    fun getPhotoFromBookmarksDatabaseById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val favourite = bookmarksRepository.getPhotoFromBookmarksDatabaseById(id)
                Log.d("DatabaseById", favourite.toString())
                assignPhoto(favourite)
                countPhotosById(id)
            }
        }
    }

    fun actionOnAddToBookmarksButton(photo: Photo){
        viewModelScope.launch {
            if (screenState.value.photoIsFavourite){
                removeFromBookmarks(photo)
                changeIsFavourite(false)
            } else {
                addToBookmarks(photo)
                changeIsFavourite(true)
            }
        }
    }

    suspend fun addToBookmarks(photo: Photo) {
        viewModelScope.launch {
            bookmarksRepository.addPhotoToBookmarksDatabase(photo)
        }

    }

    suspend fun removeFromBookmarks(photo: Photo) {
        viewModelScope.launch {
            bookmarksRepository.deletePhotoFromBookmarksDatabase(photo)
        }
    }


    suspend fun countPhotosById(id: Int) {
        viewModelScope.launch {
            val count = bookmarksRepository.countById(id)
            changeIsFavourite(count != 0)
        }
    }

}