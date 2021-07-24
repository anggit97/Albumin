package com.anggit97.domain.model

data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val user: User
)