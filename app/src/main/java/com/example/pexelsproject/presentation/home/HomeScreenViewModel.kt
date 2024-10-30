package com.example.pexelsproject.presentation.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsproject.domain.usecase.cache.CacheClearDataUseCase
import com.example.pexelsproject.domain.usecase.cache.CacheSaveDataUseCase
import com.example.pexelsproject.domain.usecase.cache.CacheGetAllDataUseCase
import com.example.pexelsproject.domain.usecase.network.NetworkGetCuratedPhotosUseCase
import com.example.pexelsproject.domain.usecase.network.NetworkGetFeaturedCollectionUseCase
import com.example.pexelsproject.domain.usecase.network.NetworkGetSearchedPhotosUseCase
import com.example.pexelsproject.utils.ExtraFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val networkGetSearchedPhotosUseCase: NetworkGetSearchedPhotosUseCase,
    private val networkGetFeaturedCollectionUseCase: NetworkGetFeaturedCollectionUseCase,
    private val networkGetCuratedPhotosUseCase: NetworkGetCuratedPhotosUseCase,
    private val cacheGetAllDataUseCase: CacheGetAllDataUseCase,
    private val cacheSaveDataUseCase: CacheSaveDataUseCase,
    private val cacheClearDataUseCase: CacheClearDataUseCase,
    @ApplicationContext private val context: Context
) : ViewModel(){

    val screenState = MutableStateFlow(HomeScreenState())

    private val _scrollEvent = Channel<Unit>()
    val scrollEvent: Flow<Unit> = _scrollEvent.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    init {
        viewModelScope.launch {
            initialAction()
        }
    }

    private suspend fun initialAction(){

        if (ExtraFunctions.checkHasInternetConnection(context)){
            getCuratedPhotos()
            getFeaturedCollection()
        }else{
            val cachedQuery = ExtraFunctions.readLastQueryFromFile(context)
            queryTextChanged(cachedQuery)

            val (cachedPhotos, cachedCollections) = cacheGetAllDataUseCase.invoke()

            screenState.update { state ->
                state.copy(
                    photos = cachedPhotos,
                    featuredCollections = cachedCollections
                )
            }
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
        if (featuredList.isNotEmpty()){
            cacheSaveDataUseCase.invokeSaveCollections(featuredList)
            Log.d("COLLECTIONS=", "SAVED collections")
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
        if (curatedList.isNotEmpty()){
            screenState.update { state ->
                state.copy(
                    cachedQuery = ""
                )
            }
            cacheSaveDataUseCase.invokeSavePhotos(curatedList)
            ExtraFunctions.writeLastQueryToFile("", context)
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
        if (queryPhotos.isNotEmpty()){
            screenState.update { state ->
                state.copy(
                    cachedQuery = query
                )
            }
            cacheSaveDataUseCase.invokeSavePhotos(queryPhotos)
            ExtraFunctions.writeLastQueryToFile(query, context)
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

    fun clearCache(){
        viewModelScope.launch {
//            cacheClearDataUseCase.invokeDeleteCollections()
            cacheClearDataUseCase.invokeDeletePhotos()
            cacheClearDataUseCase.invokeDeleteQuery()
        }
    }

}