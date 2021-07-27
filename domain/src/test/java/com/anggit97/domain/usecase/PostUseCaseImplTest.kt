package com.anggit97.domain.usecase

import app.cash.turbine.test
import com.anggit97.domain.getExpectedPostList
import com.anggit97.domain.getPostCommentList
import com.anggit97.domain.getPostList
import com.anggit97.domain.getUserList
import com.anggit97.domain.model.Comment
import com.anggit97.domain.repository.PostRepository
import com.anggit97.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
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
class PostUseCaseImplTest {

    private lateinit var sut: PostUseCase

    @MockK
    lateinit var postRepository: PostRepository

    @MockK
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = PostUseCaseImpl(postRepository, userRepository)
    }

    @Test
    fun `when call get posts, should return post list`() = runBlocking {
        //given
        coEvery { userRepository.getUsers() }.returns(flowOf(getUserList()))
        coEvery { postRepository.getPosts() }.returns(flowOf(getPostList()))

        //when
        sut.getPostList().test {
            //assert
            assertEquals(getExpectedPostList(), expectItem())
            expectComplete()
        }

        coVerifyOrder {
            userRepository.getUsers()
            postRepository.getPosts()
        }
    }

    @Test
    fun `when call get post comments, should return post comments list`() = runBlocking {
        //given
        val postId = "1"
        val expectedFirstPostComment = Comment(
            body = "comment 1", id = 1, name = "author 1"
        )

        coEvery { postRepository.getPostComment(postId) }.returns(flowOf(getPostCommentList()))

        //when
        sut.getPostComment(postId).test {
            //assert
            assertEquals(expectedFirstPostComment, expectItem().first())
            expectComplete()
        }

        coVerify(atLeast = 1) { postRepository.getPostComment(postId) }
    }
}