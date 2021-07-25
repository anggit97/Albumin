package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("address")
    val address: AddressResponse?,
    @SerialName("company")
    val company: CompanyResponse?,
    @SerialName("email")
    val email: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("username")
    val username: String?,
    @SerialName("website")
    val website: String?
)