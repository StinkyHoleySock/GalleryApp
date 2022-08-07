package com.dmitry.galleryapp.model

import android.net.Uri

data class Album(
    val id: String,
    val name: String,
    var count: Long = 0,
    var uri: Uri? = null,
)


