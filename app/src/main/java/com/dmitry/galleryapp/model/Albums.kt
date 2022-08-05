package com.dmitry.galleryapp.model

import android.os.Parcel
import android.os.Parcelable

data class Albums(
    val folderName: String?,
    val numberOfImages: String?,
    val isVideo: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(folderName)
        parcel.writeString(numberOfImages)
        parcel.writeByte(if (isVideo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Albums> {
        override fun createFromParcel(parcel: Parcel): Albums {
            return Albums(parcel)
        }

        override fun newArray(size: Int): Array<Albums?> {
            return arrayOfNulls(size)
        }
    }
}
