package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoResponse(
    @SerialName("lat")
    val lat: String?,
    @SerialName("lng")
    val lng: String?
)