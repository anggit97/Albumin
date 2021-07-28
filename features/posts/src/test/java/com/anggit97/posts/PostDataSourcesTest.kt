package com.anggit97.posts

import com.anggit97.domain.model.*


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

fun getPostList() = listOf(
    Post(
        body = "body 1", id = 1, title = "title 1", userId = 1, user = User(
            address = "",
            company = "",
            email = "",
            id = 0,
            name = "",
            username = ""
        )
    ),
    Post(
        body = "body 2", id = 2, title = "title 2", userId = 2, user = User(
            address = "",
            company = "",
            email = "",
            id = 0,
            name = "",
            username = ""
        )
    )
)

fun getPostCommentList() = listOf(
    Comment(body = "comment 1", id = 1, name = "author 1"),
    Comment(body = "comment 2", id = 2, name = "author 2")
)