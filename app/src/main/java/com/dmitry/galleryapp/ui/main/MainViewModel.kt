package com.dmitry.galleryapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dmitry.galleryapp.model.Album
import com.dmitry.galleryapp.repository.GalleryRepository

class MainViewModel(
    context: Application,
    private val galleryRepository: GalleryRepository
) : AndroidViewModel(context) {

    var albums: LiveData<List<Album>> = liveData {
        emit(galleryRepository.fetchAlbums())
    }
}
