package com.example.pexelsproject.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        val favouriteList = bookmarksRepository.getAllFavourites()

        viewModelScope.launch {
            screenState.update { state ->
                state.copy(
                    favouritePhotos = favouriteList,
                    isNoFavourite = favouriteList.isEmpty()
                )
            }
        }
    }
}