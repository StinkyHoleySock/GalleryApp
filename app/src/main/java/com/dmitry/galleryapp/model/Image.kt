package com.dmitry.galleryapp.model

import android.net.Uri

data class Image(
    val id: String,
    val name: String,
    val uri: Uri? = null,
    var count: Long = 0,
)
