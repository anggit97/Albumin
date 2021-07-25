package com.anggit97.domain.model

data class User(
    val address: String,
    val company: String,
    val email: String,
    val id: Int,
    val name: String,
    val username: String
) {

    fun getAvatarUrl() =
        "https://www.gravatar.com/avatar/94d093eda664addd6e450d7e9881bcad?s=32&d=identicon&r=PG"
}