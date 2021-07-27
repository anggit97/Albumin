package com.anggit97.posts.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.anggit97.domain.model.Comment
import com.anggit97.domain.usecase.PostUseCase
import com.anggit97.posts.MainCoroutineScopeRule
import com.anggit97.posts.getPostCommentList
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
class PostDetailViewModelTest {

    private lateinit var sut: PostDetailViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var postUseCase: PostUseCase

    private val observer = mockk<Observer<PostCommentState>>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = PostDetailViewModel(postUseCase)
    }

    @Test
    fun `when get post comments should return success`() = runBlocking {
        //given
        val expectedCommentListFirstItem = Comment(
            body = "comment 1", id = 1, name = "author 1"
        )
        val postId = "1"

        every { observer.onChanged(PostCommentState.ShowLoading) } answers {}
        every { observer.onChanged(PostCommentState.Success(getPostCommentList())) } answers {}
        every { observer.onChanged(PostCommentState.HideLoading) } answers {}
        coEvery { postUseCase.getPostComment(postId) }.returns(flowOf(getPostCommentList()))

        sut.commentState.observeForever(observer)

        //When
        sut.getPostComment(postId)

        //assert
        postUseCase.getPostComment(postId).test {
            Assert.assertEquals(expectedCommentListFirstItem, expectItem().first())
            expectComplete()
        }

        coVerifyOrder {
            postUseCase.getPostComment(postId)
            observer.onChanged(PostCommentState.ShowLoading)
            observer.onChanged(PostCommentState.Success(getPostCommentList()))
            observer.onChanged(PostCommentState.HideLoading)
        }

        clearMocks(postUseCase, observer)
    }
}