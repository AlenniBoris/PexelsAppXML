package com.example.pexelsproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.data.repository.NetworkRepositoryImpl
import com.example.pexelsproject.domain.usecase.network.NetworkGetCuratedPhotosUseCase
import com.example.pexelsproject.domain.usecase.network.NetworkGetFeaturedCollectionUseCase
import com.example.pexelsproject.domain.usecase.network.NetworkGetPhotoByIdUseCase
import com.example.pexelsproject.domain.usecase.network.NetworkGetSearchedPhotosUseCase
import com.example.pexelsproject.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val networkGetSearchedPhotosUseCase: NetworkGetSearchedPhotosUseCase,
    private val networkGetFeaturedCollectionUseCase: NetworkGetFeaturedCollectionUseCase,
    private val networkGetCuratedPhotosUseCase: NetworkGetCuratedPhotosUseCase
) : ViewModel(){

    val screenState = MutableStateFlow(HomeScreenState())

    private val _scrollEvent = Channel<Unit>()
    val scrollEvent: Flow<Unit> = _scrollEvent.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    init {
        viewModelScope.launch {
            getCuratedPhotos()
            getFeaturedCollection()
        }
    }

    fun internetRetryEventHandler(query: String){
        viewModelScope.launch {
            getFeaturedCollection()
            searchPhotoInternal(query)
        }
    }

    fun forceSearchPhoto(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            screenState.update { state ->
                state.copy(
                    queryText = query,
                    selectedFeaturedCollectionId = "",
                    featuredCollections = state.initialFeaturedCollections
                )
            }

            searchPhotoInternal(query)

        }
        viewModelScope.launch {
            searchPhotoInternal(query)
        }
    }

    private suspend fun searchPhotoInternal(query: String) {
        screenState.update {state ->
            state.copy(
                isActive = false,
                history = if (query != "") state.history + query else state.history
            )
        }
        if (query.isNotEmpty()){
            getQueryPhotos(query)
        } else {
            getCuratedPhotos()
        }
    }

    suspend fun getFeaturedCollection(){
        val featuredList = networkGetFeaturedCollectionUseCase.invoke()
        screenState.update { state ->
            state.copy(
                featuredCollections = featuredList,
                initialFeaturedCollections = featuredList
            )
        }
    }

    suspend fun getCuratedPhotos(){
        val curatedList = networkGetCuratedPhotosUseCase.invoke()
        screenState.update { state ->
            state.copy(
                errorState = curatedList.isEmpty(),
                photos = curatedList
            )
        }
    }

    suspend fun getQueryPhotos(query: String){
        val queryPhotos = networkGetSearchedPhotosUseCase.invoke(query)
        screenState.update { state ->
            state.copy(
                errorState = queryPhotos.isEmpty(),
                photos = queryPhotos
            )
        }
    }

    fun queryTextChanged(newQuery: String){
        screenState.update { state ->
            state.copy(
                queryText = newQuery,
                selectedFeaturedCollectionId = "",
                featuredCollections = state.initialFeaturedCollections
            )
        }
    }


    fun selectedFeaturedCollectionIdChanged(id: String){
        screenState.update { state ->
            val currentCollections = state.initialFeaturedCollections.toMutableList()
            val foundIndex = currentCollections.indexOfFirst { item -> item.id == id }

            if (foundIndex != -1) {
                val firstElement = currentCollections.removeAt(foundIndex)
                currentCollections.add(0, firstElement)
            }

            state.copy(
                selectedFeaturedCollectionId = id,
                featuredCollections = currentCollections.toList()
            )
        }

        viewModelScope.launch {
            _scrollEvent.send(Unit)
        }
    }

}