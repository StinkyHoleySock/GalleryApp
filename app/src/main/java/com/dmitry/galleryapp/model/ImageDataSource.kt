package com.dmitry.galleryapp.model

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore

class ImageDataSource(private val contentResolver: ContentResolver) {

    fun findImagesInAlbum(albumId: String): List<Image> {
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )
        val selection = "${MediaStore.Images.ImageColumns.BUCKET_ID} == ?"
        val selectionArgs = arrayOf(albumId)

        val findImages = HashMap<String, Image>()

        contentResolver.query(contentUri, projections, selection, selectionArgs,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} ASC")?.use { cursor ->

            if (cursor.moveToFirst()) {

                val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)


                do {
                    val mediaId = cursor.getLong(idIndex)
                    val filename = cursor.getString(displayNameIndex)
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        mediaId
                    )

                    val image = Image(
                        id = mediaId.toString(),
                        name = filename,
                        uri = uri,
                        count = 0,
                    )
                    findImages[mediaId.toString()] = image

                    image.count++

                } while (cursor.moveToNext())
            }
        }

        return findImages.values.toList().sortedByDescending { it.name }
    }
}