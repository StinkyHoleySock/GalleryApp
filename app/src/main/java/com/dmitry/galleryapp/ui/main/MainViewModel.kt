package com.dmitry.galleryapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dmitry.galleryapp.model.Album
import com.dmitry.galleryapp.repository.AlbumRepository

class MainViewModel(
    context: Application,
    private val albumRepository: AlbumRepository
) : AndroidViewModel(context) {

    var albums: LiveData<List<Album>> = liveData {
        emit(albumRepository.fetchAlbums())
    }
}
