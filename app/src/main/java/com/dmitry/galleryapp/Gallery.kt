package com.dmitry.galleryapp

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.File

class Gallery(val context: Context) {
    private val contentResolver by lazy {
        context.contentResolver
    }

    class Album(
        val id: String,
        val name: String,
        var count: Long = 0,
        var uri: Uri? = null,
        var file: File? = null,
    )

    fun findAlbums(): List<Album> {

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projections = arrayOf(

            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,

        )

        val orderBy = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"

        val findAlbums = HashMap<String, Album>()

        contentResolver.query(collection, projections, null, null, orderBy)?.use { cursor ->
            if (cursor.moveToFirst()) {

                val imageIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val bucketIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID)
                val bucketNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)

                do {
                    val bucketId = cursor.getString(bucketIdIndex)

                    val album = findAlbums[bucketId] ?: let {

                    val bucketName = cursor.getString(bucketNameIndex)
                    val imageId = cursor.getLong(imageIdIndex)
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageId
                    )
                    val album = Album(
                        id = bucketId,
                        name = bucketName,
                        uri = uri,
                        count = 1,
                    )
                        findAlbums[bucketId] = album

                        album
                    }

                    album.count++

                } while (cursor.moveToNext())
            }
        }

        return findAlbums.values.toList().sortedByDescending { it.count }
    }
}