package com.anggit97.data.repository

import app.cash.turbine.test
import com.anggit97.data.api.AlbuminApiService
import com.anggit97.data.getUserAddressResponseDummy
import com.anggit97.data.getUserResponseListDummy
import com.anggit97.data.toAddress
import com.anggit97.domain.model.User
import com.anggit97.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

/**
 * Created by Anggit Prayogo on 27,July,2021
 * GitHub : https://github.com/anggit97
 */
@ExperimentalTime
@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private lateinit var sut: UserRepository

    @MockK
    lateinit var apiService: AlbuminApiService

    private val user = User(
        address = getUserAddressResponseDummy().toAddress(),
        company = "PT XYZ",
        email = "x@example.com",
        id = 1,
        name = "example 1",
        username = "example1"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = UserRepositoryImpl(apiService)
    }


    @Test
    fun `when get users, should return list of users from service`() = runBlocking {
        //given
        val expectNameFirstUser = "example 1"
        val expectTotalUsers = 2
        val expectedFirstItem = user

        coEvery { apiService.getUsers() }.returns(getUserResponseListDummy())

        //when
        val result = sut.getUsers()

        //assert
        result.test {
            assertEquals(expectedFirstItem, expectItem().first())
            expectComplete()
        }

        result.test {
            assertEquals(expectNameFirstUser, expectItem().first().name)
            expectComplete()
        }

        result.test {
            assertEquals(expectTotalUsers, expectItem().size)
            expectComplete()
        }

        coVerify(atLeast = 1) { apiService.getUsers() }
    }

    @Test
    fun `when get users, should return empty users from service`() = runBlocking {
        //given
        val expectEmptyList = listOf<User>()
        val expectTotalItem = 0

        coEvery { apiService.getUsers() }.returns(emptyList())

        //when
        val result = sut.getUsers()

        //asert
        result.test {
            assertEquals(expectEmptyList, expectItem())
            expectComplete()
        }

        result.test {
            assertEquals(expectTotalItem, expectItem().size)
            expectComplete()
            expectNoEvents()
        }
    }

    @Test
    fun `when get users, should return error from service`() = runBlocking {
        coEvery { apiService.getUsers() }.throws(Throwable("Error!"))

        //when
        val result = sut.getUsers()

        //assert
        result.test {
            expectError()
        }
    }
}