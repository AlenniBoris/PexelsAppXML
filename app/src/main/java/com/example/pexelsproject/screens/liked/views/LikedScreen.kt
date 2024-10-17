package com.example.pexelsproject.screens.liked.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.screens.bookmarks.BookmarksScreenViewModel
import com.example.pexelsproject.screens.liked.LikedPhotosViewModel

@Composable
fun LikedScreen(
    likedPhotosViewModel: LikedPhotosViewModel = hiltViewModel(),
    bookmarksScreenViewModel: BookmarksScreenViewModel = hiltViewModel()
){

    val state by likedPhotosViewModel.screenState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                navigationAction = {
                    PexelsApplication.router.navigateTo(Screen.MainAppScreens("liked_screen"))
                }
            )
        }
    ) {
        val pd = it
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            if (state.photos.isNotEmpty() && !state.isNoLiked){
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 40.dp),

                    ) {
                    items(state.photos){likedPhoto ->
                        PhotoCards(
                            likedPhoto.src.medium,
                            likedPhoto.photographer,
                            onSwipeLeft = {
                                likedPhotosViewModel.deletePhotoFromLiked(likedPhoto)
                            },
                            onSwipeRight = {
                                bookmarksScreenViewModel.addPhotoToBookmarks(likedPhoto)
                                likedPhotosViewModel.deletePhotoFromLiked(likedPhoto)
                            }
                        )

                    }
                }
            }else if(state.isNoLiked) {
                EmptyScreen(
                    onExploreClicked = {
                        PexelsApplication.router.navigateTo(Screen.MainAppScreens("liked_screen"))
                    },
                    text = "Empty  text",
                )
            }
        }
    }
}