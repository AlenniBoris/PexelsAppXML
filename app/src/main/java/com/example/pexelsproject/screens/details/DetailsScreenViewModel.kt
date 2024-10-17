package com.example.pexelsproject.screens.details

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.example.pexelsproject.data.model.Photo
import com.example.pexelsproject.data.repository.LikedPhotosFromDatabaseRepository
import com.example.pexelsproject.data.repository.PhotosFromDatabaseRepository
import com.example.pexelsproject.data.repository.PhotosFromNetworkRepository
import com.example.pexelsproject.utils.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val photoRepository: PhotosFromNetworkRepository,
    private val bookmarksRepository: PhotosFromDatabaseRepository,
    private val connectivityRepository: ConnectivityRepository,
    private val likedRepository: LikedPhotosFromDatabaseRepository
) : ViewModel() {

    val screenState = MutableStateFlow(DetailsScreenState())

    val isOnline = connectivityRepository.isConnected.asLiveData()

    fun downloadImagesToGallery(context: Context){
        viewModelScope.launch {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(screenState.value.currentPhoto?.src?.original)
                    .submit()
                    .get()

                val savedUri = withContext(Dispatchers.IO) {
                    saveBitmapToGallery(context, bitmap)
                }

                withContext(Dispatchers.Main){
                    if (savedUri != null){
                        Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Image was not saved to gallery", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, e.message.toString() , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap): Uri?{
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val resolver = context.contentResolver

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            fos = imageUri?.let {
                resolver.openOutputStream(it)
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
            imageUri = Uri.fromFile(image)
            MediaScannerConnection.scanFile(context, arrayOf(image.toString()), null, null)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        return imageUri
    }

    fun checkInternetConnection(){
        viewModelScope.launch {
            connectivityRepository.checkInternetConnection()
        }
    }

    private fun changeIsFavourite(isFavourite: Boolean){
        screenState.update { state -> state.copy(photoIsFavourite = isFavourite) }
    }

    private fun assignPhoto(photo: Photo?){
        screenState.update { state ->
            state.copy(currentPhoto = photo)
        }
    }

    fun getPhotoFromPexelsById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val photo = photoRepository.getPhotoById(id)
                Log.d("PexelsById", photo.toString())
                assignPhoto(photo)
                countPhotosById(id)
            }
        }
    }

    fun getPhotoFromBookmarksDatabaseById(id: Int?){
        if (id != null){
            viewModelScope.launch {
                val favourite = bookmarksRepository.getPhotoFromBookmarksDatabaseById(id)
                Log.d("DatabaseById", favourite.toString())
                assignPhoto(favourite)
                countPhotosById(id)
            }
        }
    }

    fun actionOnAddToLikedButton(photo: Photo){
        viewModelScope.launch {
            if (screenState.value.photoIsLiked){
                removeFromLiked(photo)
                changeIsLiked(false)
            } else {
                addToLiked(photo)
                changeIsLiked(true)
            }
        }
    }

    suspend fun addToLiked(photoToAdd: Photo){
        likedRepository.addPhotoToLikedDatabase(photoToAdd)
    }

    suspend fun removeFromLiked(photoToDelete: Photo){
        likedRepository.deletePhotoFromLikedDatabase(photoToDelete)
    }

    private fun changeIsLiked(isFavourite: Boolean){
        screenState.update { state -> state.copy(photoIsLiked = isFavourite) }
    }


    fun actionOnAddToBookmarksButton(photo: Photo){
        viewModelScope.launch {
            if (screenState.value.photoIsFavourite){
                removeFromBookmarks(photo)
                changeIsFavourite(false)
            } else {
                addToBookmarks(photo)
                changeIsFavourite(true)
            }
        }
    }

    suspend fun addToBookmarks(photo: Photo) {
        viewModelScope.launch {
            bookmarksRepository.addPhotoToBookmarksDatabase(photo)
        }

    }

    suspend fun removeFromBookmarks(photo: Photo) {
        viewModelScope.launch {
            bookmarksRepository.deletePhotoFromBookmarksDatabase(photo)
        }
    }


    suspend fun countPhotosById(id: Int) {
        viewModelScope.launch {
            val bookmarksCount = bookmarksRepository.countById(id)
            changeIsFavourite(bookmarksCount != 0)

            val likedCount = likedRepository.countById(id)
            changeIsLiked(likedCount != 0)
        }
    }

}