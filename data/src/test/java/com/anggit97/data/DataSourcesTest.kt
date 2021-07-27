package com.anggit97.data

import com.anggit97.data.api.response.AddressResponse
import com.anggit97.data.api.response.CompanyResponse
import com.anggit97.data.api.response.UserResponse
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
