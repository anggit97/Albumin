package com.anggit97.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("albumId")
    val albumId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String
)