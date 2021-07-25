package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    @SerialName("city")
    val city: String?,
    @SerialName("geo")
    val geo: GeoResponse?,
    @SerialName("street")
    val street: String?,
    @SerialName("suite")
    val suite: String?,
    @SerialName("zipcode")
    val zipcode: String?
)