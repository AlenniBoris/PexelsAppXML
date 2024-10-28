package com.example.pexelsproject.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksAddPhotoToDatabaseUseCase
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksGetAllPhotosFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenViewModel @Inject constructor(
    private val bookmarksGetAllPhotosFromDatabaseUseCase: BookmarksGetAllPhotosFromDatabaseUseCase,
    private val bookmarksAddPhotoToDatabaseUseCase: BookmarksAddPhotoToDatabaseUseCase
) : ViewModel() {
    val screenState = MutableStateFlow(BookmarksScreenState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFavouritePhotosInit()
        }
    }

    suspend fun getFavouritePhotosInit(){
        val favouriteList = bookmarksGetAllPhotosFromDatabaseUseCase.invoke()

        viewModelScope.launch{
            screenState.update { state ->
                state.copy(
                    favouritePhotos = favouriteList,
                    isNoFavourite = favouriteList.isEmpty()
                )
            }
        }
    }

    fun addPhotoToBookmarks(photo: Photo){
        viewModelScope.launch(Dispatchers.IO) {
            addPhotoToBookmarksInit(photo)
        }
    }

    suspend fun addPhotoToBookmarksInit(photo: Photo){
        bookmarksAddPhotoToDatabaseUseCase.invoke(photo)
    }
}