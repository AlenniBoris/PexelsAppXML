package com.example.pexelsproject.presentation.details

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
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.pexelsproject.domain.model.Photo
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksAddPhotoToDatabaseUseCase
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksCountByIdUseCase
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksDeletePhotoFromDatabaseUseCase
import com.example.pexelsproject.domain.usecase.bookmarks.BookmarksGetPhotoFromDatabaseByIdUseCase
import com.example.pexelsproject.domain.usecase.liked.LikedAddPhotoToDatabaseUseCase
import com.example.pexelsproject.domain.usecase.liked.LikedCountByIdUseCase
import com.example.pexelsproject.domain.usecase.liked.LikedDeletePhotoFromLikedDatabase
import com.example.pexelsproject.domain.usecase.network.NetworkGetPhotoByIdUseCase
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
    private val networkGetPhotoByIdUseCase: NetworkGetPhotoByIdUseCase,
    private val bookmarksDeletePhotoFromDatabaseUseCase: BookmarksDeletePhotoFromDatabaseUseCase,
    private val bookmarksAddPhotoFromDatabaseByIdUseCase: BookmarksAddPhotoToDatabaseUseCase,
    private val bookmarksGetPhotoByIdUseCase: BookmarksGetPhotoFromDatabaseByIdUseCase,
    private val bookmarksCountByIdUseCase: BookmarksCountByIdUseCase,
    private val likedCountByIdUseCase: LikedCountByIdUseCase,
    private val likedAddPhotoToDatabaseUseCase: LikedAddPhotoToDatabaseUseCase,
    private val likedDeletePhotoToDatabaseUseCase: LikedDeletePhotoFromLikedDatabase,
) : ViewModel() {

    val screenState = MutableStateFlow(DetailsScreenState())

    fun downloadImagesToGallery(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadImagesInit(context)
        }
    }

    private suspend fun downloadImagesInit(context: Context) {
        try {
            val bitmap = withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(screenState.value.currentPhoto?.src?.original)
                    .submit()
                    .get()
            }

            val savedUri = withContext(Dispatchers.IO) {
                saveBitmapToGallery(context, bitmap)
            }

            withContext(Dispatchers.Main) {
                if (savedUri != null) {
                    Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Image was not saved to gallery", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap): Uri? {
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
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
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

    fun loadImageAction(prevDestination: String, pictureId: Int) {
        if (prevDestination == "home_screen") {
            getPhotoFromPexelsById(pictureId)
        } else {
            getPhotoFromBookmarksDatabaseById(pictureId)
        }
    }

    private fun changeIsFavourite(isFavourite: Boolean) {
        screenState.update { state -> state.copy(photoIsFavourite = isFavourite) }
    }

    private fun assignPhoto(photo: Photo?) {
        screenState.update { state ->
            val someErrorOccurred = photo == null

            state.copy(
                currentPhoto = photo,
                someErrorOccurred = someErrorOccurred
            )
        }
    }

    private fun getPhotoFromPexelsById(id: Int?) {
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val photo = networkGetPhotoByIdUseCase.invoke(id)
                Log.d("PexelsById", photo.toString())
                assignPhoto(photo)
                countPhotosById(id)
            }
        }
    }

    private fun getPhotoFromBookmarksDatabaseById(id: Int?) {
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val favourite = bookmarksGetPhotoByIdUseCase.invoke(id)
                Log.d("DatabaseById", favourite.toString())
                assignPhoto(favourite)
                countPhotosById(id)
            }
        }
    }

    fun actionOnAddToLikedButton(photo: Photo) {
        viewModelScope.launch {
            if (screenState.value.photoIsLiked) {
                removeFromLiked(photo)
                changeIsLiked(false)
            } else {
                addToLiked(photo)
                changeIsLiked(true)
            }
        }
    }

    private suspend fun addToLiked(photoToAdd: Photo) {
        likedAddPhotoToDatabaseUseCase.invoke(photoToAdd)
    }

    private suspend fun removeFromLiked(photoToDelete: Photo) {
        likedDeletePhotoToDatabaseUseCase.invoke(photoToDelete)
    }

    private fun changeIsLiked(isFavourite: Boolean) {
        screenState.update { state -> state.copy(photoIsLiked = isFavourite) }
    }


    fun actionOnAddToBookmarksButton(photo: Photo) {
        viewModelScope.launch {
            if (screenState.value.photoIsFavourite) {
                removeFromBookmarks(photo)
                changeIsFavourite(false)
            } else {
                addToBookmarks(photo)
                changeIsFavourite(true)
            }
        }
    }

    private suspend fun addToBookmarks(photo: Photo) {
        viewModelScope.launch {
            bookmarksAddPhotoFromDatabaseByIdUseCase.invoke(photo)
        }

    }

    private suspend fun removeFromBookmarks(photo: Photo) {
        viewModelScope.launch {
            bookmarksDeletePhotoFromDatabaseUseCase.invoke(photo)
        }
    }


    private suspend fun countPhotosById(id: Int) {
        viewModelScope.launch {
            val bookmarksCount = bookmarksCountByIdUseCase.invoke(id)
            changeIsFavourite(bookmarksCount != 0)

            val likedCount = likedCountByIdUseCase.invoke(id)
            changeIsLiked(likedCount != 0)
        }
    }

}