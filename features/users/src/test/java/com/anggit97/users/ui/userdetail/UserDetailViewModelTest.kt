package com.anggit97.users.ui.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.domain.usecase.UserUseCase
import com.anggit97.users.MainCoroutineScopeRule
import com.anggit97.users.getUserAlbumsListDummy
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.time.ExperimentalTime

/**
 * Created by Anggit Prayogo on 27,July,2021
 * GitHub : https://github.com/anggit97
 */
@ExperimentalTime
@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    private lateinit var sut: UserDetailViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var userUseCase: UserUseCase

    private val observerAlbum = mockk<Observer<AlbumState>>()
    private val observerPhoto = mockk<Observer<List<Photo>>>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = UserDetailViewModel(userUseCase)
    }

    @Test
    fun `when get user albums, should return success`() = runBlocking {
        //given
        val expectedUserAlbumListFirstItem = Album(
            id = 1, title = "album 1", userId = 1, photos = listOf()
        )
        val userId = "1"

        every { observerAlbum.onChanged(AlbumState.ShowLoading) } answers {}
        every { observerAlbum.onChanged(AlbumState.Success(getUserAlbumsListDummy())) } answers {}
        coEvery { userUseCase.getUserAlbums(userId) }.returns(flowOf(getUserAlbumsListDummy()))

        sut.album.observeForever(observerAlbum)


        //when
        sut.getUserAlbums(userId)


        //assert
        userUseCase.getUserAlbums(userId).test {
            Assert.assertEquals(expectedUserAlbumListFirstItem, expectItem().first())
            expectComplete()
        }

        coVerifyOrder {
            userUseCase.getUserAlbums(userId)
            observerAlbum.onChanged(AlbumState.ShowLoading)
            observerAlbum.onChanged(AlbumState.Success(getUserAlbumsListDummy()))
        }

        clearMocks(userUseCase, observerAlbum)
    }
}