package com.anggit97.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String,
    val albumId: Int
) : Parcelable