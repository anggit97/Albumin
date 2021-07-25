package com.anggit97.domain.usecase

import com.anggit97.domain.model.Comment
import com.anggit97.domain.model.Post
import com.anggit97.domain.repository.PostRepository
import com.anggit97.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */

interface PostUseCase {

    suspend fun getPostList(): Flow<List<Post>>

    suspend fun getPostComment(postId: String): Flow<List<Comment>>
}

@ExperimentalCoroutinesApi
class PostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) : PostUseCase {

    override suspend fun getPostList(): Flow<List<Post>> {
        val users = userRepository.getUsers()
        val posts = postRepository.getPosts()
        return combine(users, posts) { usersList, postsList ->
            postsList.map { post ->
                val result = post.copy()
                result.user = usersList.first { it.id == post.userId }
                result
            }
        }
    }

    override suspend fun getPostComment(postId: String): Flow<List<Comment>> {
        return postRepository.getPostComment(postId)
    }
}
