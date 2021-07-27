package com.anggit97.data.repository

import app.cash.turbine.test
import com.anggit97.data.*
import com.anggit97.data.api.AlbuminApiService
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
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

    private val expectedUser = User(
        address = getUserAddressResponseDummy().toAddress(),
        company = "PT XYZ",
        email = "x@example.com",
        id = 1,
        name = "example 1",
        username = "example1"
    )

    private val expectedAlbum = Album(
        id = 1,
        title = "Album 1",
        userId = 1,
        photos = listOf()
    )

    private val expectedPhoto = Photo(
        id = 1,
        thumbnailUrl = "https://via.placeholder.com/150/92c952",
        title = "photo 1",
        url = "https://via.placeholder.com/600/92c952",
        albumId = 1
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
        val expectedFirstItem = expectedUser

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

        coVerify(atLeast = 1) { apiService.getUsers() }
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

        coVerify(atLeast = 1) { apiService.getUsers() }
    }

    @Test
    fun `when get user albums, should return list of user albums from service`() = runBlocking {
        //given
        val expectedFirstItem = expectedAlbum
        val userId = "1"

        coEvery { apiService.getUserAlbums(userId) }.returns(getUserAlbumsResponseList())

        //when
        val result = sut.getUserAlbums(userId)

        //assert
        result.test {
            assertEquals(expectedFirstItem, expectItem().first())
            expectComplete()
        }

        coVerify(atLeast = 1) { apiService.getUserAlbums(userId) }
    }

    @Test
    fun `when get user albums, should return empty user albums from service`() = runBlocking {
        //given
        val expectEmptyList = listOf<Album>()
        val expectTotalItem = 0
        val userId = "1"

        coEvery { apiService.getUserAlbums(userId) }.returns(emptyList())

        //when
        val result = sut.getUserAlbums(userId)

        //asert
        result.test {
            assertEquals(expectEmptyList, expectItem())
            expectComplete()
        }

        result.test {
            assertEquals(expectTotalItem, expectItem().size)
            expectComplete()
        }

        coVerify(atLeast = 1) { apiService.getUserAlbums(userId) }
    }

    @Test
    fun `when get user albums, should return error from service`() = runBlocking {
        val userId = "1"
        coEvery { apiService.getUserAlbums(userId) }.throws(Throwable("Error!"))

        //when
        val result = sut.getUserAlbums(userId)

        //assert
        result.test {
            expectError()
        }

        coVerify(atLeast = 1) { apiService.getUserAlbums(userId) }
    }

    @Test
    fun `when get album photos, should return list of photos from service`() = runBlocking {
        //given
        val expectedFirstItem = expectedPhoto
        val albumId = "1"

        coEvery { apiService.getAlbumPhotos(albumId) }.returns(getAlbumPhotosResponseList())

        //when
        val result = sut.getAlbumPhotos(albumId)

        //assert
        result.test {
            assertEquals(expectedFirstItem, expectItem().first())
            expectComplete()
        }

        coVerify(atLeast = 1) { apiService.getAlbumPhotos(albumId) }
    }

    @Test
    fun `when get album photos, should return empty photo list from service`() = runBlocking {
        //given
        val expectEmptyList = listOf<Photo>()
        val expectTotalItem = 0
        val albumId = "1"

        coEvery { apiService.getAlbumPhotos(albumId) }.returns(emptyList())

        //when
        val result = sut.getAlbumPhotos(albumId)

        //asert
        result.test {
            assertEquals(expectEmptyList, expectItem())
            expectComplete()
        }

        result.test {
            assertEquals(expectTotalItem, expectItem().size)
            expectComplete()
        }

        coVerify(atLeast = 1) { apiService.getAlbumPhotos(albumId) }
    }

    @Test
    fun `when get album photos, should return error from service`() = runBlocking {
        val albumId = "1"
        coEvery { apiService.getAlbumPhotos(albumId) }.throws(Throwable("Error!"))

        //when
        val result = sut.getAlbumPhotos(albumId)

        //assert
        result.test {
            expectError()
        }

        coVerify(atLeast = 1) { apiService.getAlbumPhotos(albumId) }
    }
}