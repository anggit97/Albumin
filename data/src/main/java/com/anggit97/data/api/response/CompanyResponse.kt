package com.anggit97.data.api.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse(
    @SerialName("bs")
    val bs: String?,
    @SerialName("catchPhrase")
    val catchPhrase: String?,
    @SerialName("name")
    val name: String?
)