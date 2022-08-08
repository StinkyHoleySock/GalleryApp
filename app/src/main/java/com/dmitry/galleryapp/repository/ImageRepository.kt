package com.dmitry.galleryapp.repository

import com.dmitry.galleryapp.model.ImageDataSource
import com.dmitry.galleryapp.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ImageRepository(
    private val image: ImageDataSource,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchImages(albumId: String): List<Image> {
        return withContext(dispatcher) {
            image.findImagesInAlbum(albumId)
        }
    }
}