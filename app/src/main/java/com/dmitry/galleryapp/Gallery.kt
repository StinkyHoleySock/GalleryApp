package com.dmitry.galleryapp

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import com.dmitry.galleryapp.model.Album
import com.dmitry.galleryapp.model.Image

class Gallery(private val context: Context) {
    private val contentResolver by lazy {
        context.contentResolver
    }

    fun findAlbums(): List<Album> {

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
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
                        count = 0,
                    )
                        findAlbums[bucketId] = album

                        album
                    }

                    album.count++

                } while (cursor.moveToNext())
            }
        }

        return findAlbums.values.toList().sortedBy { it.name }
    }

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

    fun renameFile(uri: Uri, newName: String) {
        //DocumentsContract.renameDocument(contentResolver, uri, newName)
    }
}
