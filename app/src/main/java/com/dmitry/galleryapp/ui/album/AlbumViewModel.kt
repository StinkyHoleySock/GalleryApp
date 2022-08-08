package com.dmitry.galleryapp.ui.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dmitry.galleryapp.model.Image
import com.dmitry.galleryapp.repository.GalleryRepository

class AlbumViewModel(
    albumId: String?,
    context: Application,
    private val galleryRepository: GalleryRepository
) : AndroidViewModel(context) {

    var images: LiveData<List<Image>> = liveData {
        albumId?.let { galleryRepository.fetchImages(it) }?.let { emit(it) }
    }
}