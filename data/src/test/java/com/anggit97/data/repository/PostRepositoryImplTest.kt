package com.anggit97.data.repository

import app.cash.turbine.test
import com.anggit97.data.api.AlbuminApiService
import com.anggit97.data.getPostResponseList
import com.anggit97.data.getUserAddressResponseDummy
import com.anggit97.data.toAddress
import com.anggit97.domain.model.Post
import com.anggit97.domain.model.User
import com.anggit97.domain.repository.PostRepository
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
class PostRepositoryImplTest {

    private lateinit var sut: PostRepository

    @MockK
    lateinit var apiService: AlbuminApiService

    private val expectedUser = User(
        address = "",
        company = "",
        email = "",
        id = 0,
        name = "",
        username = ""
    )

    private val expectedPost = Post(
        body = "body 1", id = 1, title = "title 1", userId = 1, user = expectedUser
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = PostRepositoryImpl(apiService)
    }

    @Test
    fun `when get users, should return list of users from service`() = runBlocking {
        //given
        val expectTitleFirstPost = "title 1"
        val expectTotalPosts = 2
        val expectedFirstItem = expectedPost

        coEvery { apiService.getPosts() }.returns(getPostResponseList())

        //when
        val result = sut.getPosts()

        //assert
        result.test {
            assertEquals(expectedFirstItem, expectItem().first())
            expectComplete()
        }

        result.test {
            assertEquals(expectTitleFirstPost, expectItem().first().title)
            expectComplete()
        }

        result.test {
            assertEquals(expectTotalPosts, expectItem().size)
            expectComplete()
        }

        coVerify(atLeast = 1) { apiService.getPosts() }
    }

    @Test
    fun `when get posts, should return empty posts from service`() = runBlocking {
        //given
        val expectEmptyList = listOf<Post>()
        val expectTotalItem = 0

        coEvery { apiService.getPosts() }.returns(emptyList())

        //when
        val result = sut.getPosts()

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

        coVerify(atLeast = 1) { apiService.getPosts() }
    }

    @Test
    fun `when get posts, should return error from service`() = runBlocking {
        coEvery { apiService.getPosts() }.throws(Throwable("Error!"))

        //when
        val result = sut.getPosts()

        //assert
        result.test {
            expectError()
        }

        coVerify(atLeast = 1) { apiService.getPosts() }
    }
}