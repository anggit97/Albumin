package com.anggit97.domain.model

data class Album(
    val id: Int,
    val title: String,
    val userId: Int,
    val photos: List<Photo>
)