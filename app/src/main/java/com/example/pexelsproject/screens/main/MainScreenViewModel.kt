package com.example.pexelsproject.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.data.repository.PhotosFromNetworkRepository
import com.example.pexelsproject.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
class MainScreenViewModel @Inject constructor(
    private val photoRepository: PhotosFromNetworkRepository
) : ViewModel(){

    private val searchFlow = MutableSharedFlow<String>()
    val screenState = MutableStateFlow(MainScreenState())

    private val _scrollEvent = Channel<Unit>()
    val scrollEvent: Flow<Unit> = _scrollEvent.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    init {
        viewModelScope.launch {
            getCuratedPhotos()
            getFeaturedCollection()
        }
        searchFlow
            .debounce(2500L)
            .onEach { query -> searchPhotoInternal(query) }
            .launchIn(viewModelScope)
    }

    fun searchPhoto(query: String) {
        viewModelScope.launch {
            screenState.update { state ->
                state.copy(
                    queryText = query,
                    selectedFeaturedCollectionId = "",
                    featuredCollections = state.initialFeaturedCollections
                )
            }

            searchFlow.emit(query)
        }
    }

    fun forceSearchPhoto(query: String) {
        viewModelScope.launch {
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

    fun changeIsActive(isActive: Boolean) {
        screenState.update { state ->
            state.copy(
                isActive = isActive
            )
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
        val featuredList = photoRepository.getFeaturedCollectionsList(
            perPage = Constants.NUMBER_FEATURED,
            page = Constants.PER_PAGE
        )
        screenState.update { state ->
            state.copy(
                featuredCollections = featuredList,
                initialFeaturedCollections = featuredList
            )
        }
    }

    suspend fun getCuratedPhotos(){
        val curatedList = photoRepository.getCuratedPhotosList(
            page = Constants.NUMBER_CURATED,
            perPage = Constants.PER_PAGE
        )
        screenState.update { state ->
            state.copy(
                errorState = curatedList.isEmpty(),
                photos = curatedList
            )
        }
    }

    suspend fun getQueryPhotos(query: String){
        val queryPhotos = photoRepository.getSearchedPhotosList(
            query = query,
            page = Constants.NUMBER_CURATED,
            perPage = Constants.PER_PAGE
        )
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


}