package com.anggit97.data

import com.anggit97.data.api.response.*
import com.anggit97.domain.model.User
import java.io.InputStreamReader


/**
 * Created by Anggit Prayogo on 27,July,2021
 * GitHub : https://github.com/anggit97
 */
//fun <T> load(clazz: Class<T>, file: String): T {
//    val fixtureStreamReader = InputStreamReader(clazz.classLoader?.getResourceAsStream(file))
//    return Gson().fromJson(fixtureStreamReader, clazz)
//}

fun getUser() = User(
    address = getUserAddressResponseDummy().toAddress(),
    company = "PT XYZ",
    email = "x@example.com",
    id = 1,
    name = "example 1",
    username = "example1"
)

fun AddressResponse.toAddress() = "${this.street} ${this.suite}, ${this.city}"

fun getUserResponseListDummy() = listOf(
    UserResponse(
        address = getUserAddressResponseDummy(),
        company = getUserCompanyResponseDummy(),
        email = "x@example.com",
        id = 1,
        name = "example 1",
        phone = "+62yyyy",
        username = "example1",
        website = "example1.com"
    ),
    UserResponse(
        address = getUserAddressResponseDummy(),
        company = getUserCompanyResponseDummy(),
        email = "y@example.com",
        id = 2,
        name = "example 2",
        phone = "+62xxxx",
        username = "example2",
        website = "example2.com"
    ),
)

fun getUserAddressResponseDummy() = AddressResponse(
    city = "Tangerang", geo = null, street = "Pondok Kacang", suite = null, zipcode = null
)

fun getUserCompanyResponseDummy() = CompanyResponse(
    bs = null, catchPhrase = null, name = "PT XYZ"
)

fun getUserAlbumsResponseList() = listOf(
    AlbumResponse(
        id = 1,
        title = "Album 1",
        userId = 1
    ),
    AlbumResponse(
        id = 1,
        title = "Album 2",
        userId = 1
    )
)

fun getAlbumPhotosResponseList() = listOf(
    PhotoResponse(
        id = 1,
        thumbnailUrl = "https://via.placeholder.com/150/92c952",
        title = "photo 1",
        url = "https://via.placeholder.com/600/92c952",
        albumId = 1
    ),
    PhotoResponse(
        id = 2,
        thumbnailUrl = "https://via.placeholder.com/150/92c952",
        title = "photo 2",
        url = "https://via.placeholder.com/600/92c952",
        albumId = 1
    )
)

fun getPostResponseList() = listOf(
    PostResponse(
        body = "body 1", id = 1, title = "title 1", userId = 1
    ),
    PostResponse(
        body = "body 2", id = 2, title = "title 2", userId = 2
    )
)
