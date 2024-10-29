package com.example.pexelsproject.presentation.liked.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pexelsproject.R
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.presentation.bookmarks.BookmarksScreenViewModel
import com.example.pexelsproject.presentation.liked.LikedPhotosViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun LikedScreen(
    likedPhotosViewModel: LikedPhotosViewModel = hiltViewModel(),
    bookmarksScreenViewModel: BookmarksScreenViewModel = hiltViewModel(),
){

    val state by likedPhotosViewModel.screenState.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.background),
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
                .fillMaxSize(),
        ){
            val context = LocalContext.current
            val deletedFromLiked: String =  stringResource(R.string.deleted_from_liked_text)
            val addedToBookmarks: String =  stringResource(R.string.added_to_bookmarks)


            if (state.photos.isNotEmpty() && !state.isNoLiked){
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues = pd)
                        .padding(horizontal = 16.dp)
                    ) {
                    items(state.photos){likedPhoto ->
                        PhotoCards(
                            likedPhoto.src.medium,
                            likedPhoto.photographer,
                            onSwipeLeft = {
                                likedPhotosViewModel.deletePhotoFromLiked(likedPhoto)
                                Toast.makeText(
                                    context,
                                    deletedFromLiked,
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onSwipeRight = {
                                bookmarksScreenViewModel.addPhotoToBookmarks(likedPhoto)
                                likedPhotosViewModel.deletePhotoFromLiked(likedPhoto)
                                Toast.makeText(
                                    context,
                                    addedToBookmarks,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )

                    }
                }
            }else if(state.isNoLiked) {
                EmptyScreen(
                    onExploreClicked = {
                        PexelsApplication.router.navigateTo(Screen.MainAppScreens("liked_screen"))
                    },
                    text = stringResource(R.string.noting_saved_yet_text),
                )
            }
        }
    }
}