package com.anggit97.domain.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class User(
    val address: String,
    val company: String,
    val email: String,
    val id: Int,
    val name: String,
    val username: String
) : Parcelable {

    fun getAvatarUrl() =
        "https://www.gravatar.com/avatar/94d093eda664addd6e450d7e9881bcad?s=32&d=identicon&r=PG"
}