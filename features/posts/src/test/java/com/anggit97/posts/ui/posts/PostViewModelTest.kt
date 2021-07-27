package com.anggit97.posts.ui.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.anggit97.domain.model.Post
import com.anggit97.domain.model.User
import com.anggit97.domain.usecase.PostUseCase
import com.anggit97.posts.getPostList
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
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
class PostViewModelTest {

    private lateinit var sut: PostViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var postUseCase: PostUseCase

    private val observer = mockk<Observer<PostListState>>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        sut = PostViewModel(postUseCase)
    }


    @Test
    fun `when get posts, should return success`() = runBlocking {
        //given
        val expectedPostListFirstItem = Post(
            body = "body 1", id = 1, title = "title 1", userId = 1, user = User(
                address = "",
                company = "",
                email = "",
                id = 0,
                name = "",
                username = ""
            )
        )

        every { observer.onChanged(PostListState.ShowLoading) } answers {}
        every { observer.onChanged(PostListState.Success(getPostList())) } answers {}
        every { observer.onChanged(PostListState.HideLoading) } answers {}
        coEvery { postUseCase.getPostList() }.returns(flowOf(getPostList()))

        sut.postState.observeForever(observer)


        //when
        sut.getPosts()


        //assert
        postUseCase.getPostList().test {
            assertEquals(expectedPostListFirstItem, expectItem().first())
            expectComplete()
        }

        coVerifyOrder {
            postUseCase.getPostList()
            observer.onChanged(PostListState.ShowLoading)
            observer.onChanged(PostListState.Success(getPostList()))
            observer.onChanged(PostListState.HideLoading)
        }

        clearMocks(postUseCase, observer)
    }

    @Test
    fun `given post list and id, should return single valid post`() {
        //given
        val postId = 1
        val postList = getPostList()
        val expectedPost = getPostList().first()

        //when
        val result = sut.getPostDetailById(postId.toString(), postList)

        //assert
        assertEquals(expectedPost, result)
    }
}