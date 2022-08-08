package com.dmitry.galleryapp.repository

import com.dmitry.galleryapp.GalleryDataSource
import com.dmitry.galleryapp.model.Album
import com.dmitry.galleryapp.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GalleryRepository(
    private val gallery: GalleryDataSource,
    private val dispatcher: CoroutineDispatcher) {

    suspend fun fetchAlbums(): List<Album> {
        return withContext(dispatcher) {
            gallery.findAlbums()
        }
    }

    suspend fun fetchImages(albumId: String): List<Image> {
        return withContext(dispatcher) {
            gallery.findImagesInAlbum(albumId)
        }
    }
}

