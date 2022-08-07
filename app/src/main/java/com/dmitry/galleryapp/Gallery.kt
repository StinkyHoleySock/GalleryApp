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

    class Image(
        val id: String,
        val name: String,
        val uri: Uri? = null,
        var count: Long = 0,
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

    fun findImagesInAlbum(albumId: String): List<Image> {
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.ORIENTATION,
            MediaStore.Images.ImageColumns.WIDTH,
            MediaStore.Images.ImageColumns.HEIGHT,
            MediaStore.Images.ImageColumns.SIZE,
            @Suppress("DEPRECATION")
            MediaStore.Images.ImageColumns.LATITUDE,
            @Suppress("DEPRECATION")
            MediaStore.Images.ImageColumns.LONGITUDE
        )
        val selection = "${MediaStore.Images.ImageColumns.BUCKET_ID} == ?"
        val selectionArgs = arrayOf(albumId)

        val findImages = HashMap<String, Image>()

        contentResolver.query(contentUri, projections, selection, selectionArgs,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} ASC")?.use { cursor ->

            val totalCount = cursor.count

            if (cursor.moveToFirst()) {

                val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val dateTakenIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)
                val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)

                val orientationIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION)
                val widthIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.WIDTH)
                val heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.HEIGHT)
                val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE)
                @Suppress("DEPRECATION")
                val latIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE)
                @Suppress("DEPRECATION")
                val lonIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE)

                do {
                    val mediaId = cursor.getLong(idIndex)
                    val filename = cursor.getString(displayNameIndex)
                    val millis = cursor.getLong(dateTakenIndex)
                    val orientation = cursor.getInt(orientationIndex)
                    val width = cursor.getInt(widthIndex)
                    val height = cursor.getInt(heightIndex)
                    val size = cursor.getLong(sizeIndex)

                    // scoped storage - access file via uri instead of filepath + filename
                    var uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaId)

                    val image = Image(
                        id = mediaId.toString(),
                        name = filename,
                        uri = uri,
                        count = 1,
                    )
                    findImages[mediaId.toString()] = image

                    image.count++

                } while (cursor.moveToNext())
            }
        }

        return findImages.values.toList().sortedByDescending { it.name }
    }
}
