package com.anggit97.data.repository.mapper

import com.anggit97.data.api.response.AddressResponse
import com.anggit97.data.api.response.PostResponse
import com.anggit97.data.api.response.UserResponse
import com.anggit97.domain.model.Post
import com.anggit97.domain.model.User


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
fun List<PostResponse>.toPostList() = map {
    Post(
        body = it.body ?: "", id = it.id, title = it.title ?: "", userId = it.userId,
        user = User(
            address = "",
            company = "",
            email = "",
            id = 0,
            name = "",
            username = ""
        )
    )
}

fun List<UserResponse>.toUserList() = map {
    User(
        address = it.address?.toAddress() ?: "-",
        company = it.company?.name ?: "-",
        email = it.email ?: "-",
        id = it.id,
        name = it.name ?: "-",
        username = it.username ?: "-",
    )
}

fun AddressResponse.toAddress() = "${this.street} ${this.suite}, ${this.city}"


