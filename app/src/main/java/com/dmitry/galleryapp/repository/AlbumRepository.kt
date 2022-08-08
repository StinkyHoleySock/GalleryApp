package com.dmitry.galleryapp.repository

import com.dmitry.galleryapp.model.AlbumDataSource
import com.dmitry.galleryapp.model.Album
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(
    private val album: AlbumDataSource,
    private val dispatcher: CoroutineDispatcher) {

    suspend fun fetchAlbums(): List<Album> {
        return withContext(dispatcher) {
            album.findAlbums()
        }
    }

}

