package com.anggit97.domain.usecase

import app.cash.turbine.test
import com.anggit97.domain.getAlbumPhotosListAlbum
import com.anggit97.domain.getUserAlbumsListDummy
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class UserUseCaseImplTest {

    private lateinit var sut: UserUseCase

    @MockK
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = UserUseCaseImpl(userRepository)
    }

    @Test
    fun `when call user repository, should return user albums list`(): Unit = runBlocking {
        //given
        val userId = "1"
        val expectedFirstAlbum = Album(
            id = 1, title = "album 1", userId = 1, photos = listOf()
        )

        coEvery {
            userRepository.getUserAlbums(userId)
        }.returns(
            flowOf(getUserAlbumsListDummy())
        )

        sut.getUserAlbums(userId).test {
            assertEquals(expectedFirstAlbum, expectItem().first())
            expectComplete()
        }

        coVerify(atLeast = 1) { userRepository.getUserAlbums(userId) }
    }

    @Test
    fun `when call user repository, should return album photos list`(): Unit = runBlocking {
        //given
        val albumId = "1"
        val expectedFirstPhoto = Photo(
            id = 1,
            thumbnailUrl = "https://example.com/gambar.png",
            title = "title 1",
            url = "https://example.com/gambar.png",
            albumId = 1
        )

        coEvery {
            userRepository.getAlbumPhotos(albumId)
        }.returns(
            flowOf(getAlbumPhotosListAlbum())
        )

        sut.getAlbumPhotos(albumId).test {
            assertEquals(expectedFirstPhoto, expectItem().first())
            expectComplete()
        }

        coVerify(atLeast = 1) { userRepository.getAlbumPhotos(albumId) }
    }
}