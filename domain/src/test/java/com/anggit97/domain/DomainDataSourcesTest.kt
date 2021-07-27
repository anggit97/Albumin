package com.anggit97.domain

import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo


/**
 * Created by Anggit Prayogo on 27,July,2021
 * GitHub : https://github.com/anggit97
 */

fun getUserAlbumsListDummy() = listOf(
    Album(id = 1, title = "album 1", userId = 1, photos = listOf()),
    Album(id = 2, title = "album 2", userId = 1, photos = listOf())
)

fun getAlbumPhotosListAlbum() = listOf(
    Photo(
        id = 1,
        thumbnailUrl = "https://example.com/gambar.png",
        title = "title 1",
        url = "https://example.com/gambar.png",
        albumId = 1
    ),
    Photo(
        id = 2,
        thumbnailUrl = "https://example.com/gambar.png",
        title = "title 2",
        url = "https://example.com/gambar.png",
        albumId = 1
    )
)
