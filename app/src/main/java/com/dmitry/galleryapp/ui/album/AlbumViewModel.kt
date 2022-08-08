package com.dmitry.galleryapp.ui.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dmitry.galleryapp.model.Image
import com.dmitry.galleryapp.repository.ImageRepository

class AlbumViewModel(
    albumId: String?,
    context: Application,
    private val imageRepository: ImageRepository
) : AndroidViewModel(context) {

    var images: LiveData<List<Image>> = liveData {
        albumId?.let { imageRepository.fetchImages(albumId) }?.let { emit(it) }
    }
}