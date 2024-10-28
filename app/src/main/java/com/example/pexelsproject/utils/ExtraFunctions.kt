package com.example.pexelsproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.pexelsproject.presentation.home.HomeScreenViewModel
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

    fun checkHasInternetConnection(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}