package com.anggit97.domain.model

data class Album(
    val id: Int,
    val title: String,
    val userId: Int,
    var photos: List<Photo> = listOf()
)