package com.example.pexelsproject.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.repository.PhotosFromDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenViewModel @Inject constructor(
    private val bookmarksRepository: PhotosFromDatabaseRepository
) : ViewModel() {
    val screenState = MutableStateFlow(BookmarksScreenState())

    init {
        viewModelScope.launch {
            getFavouritePhotosInit()
        }
    }

    suspend fun getFavouritePhotosInit(){
        val favouriteList = bookmarksRepository.getAllPhotosFromBookmarksDatabase()

        viewModelScope.launch {
            screenState.update { state ->
                state.copy(
                    favouritePhotos = favouriteList,
                    isNoFavourite = favouriteList.isEmpty()
                )
            }
        }
    }

    fun addPhotoToBookmarks(photo: Photo){
        viewModelScope.launch {
            addPhotoToBookmarksInit(photo)
        }
    }

    suspend fun addPhotoToBookmarksInit(photo: Photo){
        bookmarksRepository.addPhotoToBookmarksDatabase(photo)
    }
}