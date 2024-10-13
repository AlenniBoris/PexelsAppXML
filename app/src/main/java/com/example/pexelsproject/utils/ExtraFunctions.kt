package com.example.pexelsproject.utils

import com.example.pexelsproject.screens.home.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object ExtraFunctions {
    fun changeSearch(scope: CoroutineScope, mainScreenViewModel: HomeScreenViewModel, title: String, id: String){
        mainScreenViewModel.queryTextChanged(title)
        mainScreenViewModel.selectedFeaturedCollectionIdChanged(id)
        scope.launch {
            mainScreenViewModel.getQueryPhotos(title)
        }
    }
}