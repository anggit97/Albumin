package com.anggit97.data.repository

import com.anggit97.data.api.AlbuminApiService
import com.anggit97.data.repository.mapper.toCommentList
import com.anggit97.data.repository.mapper.toPostList
import com.anggit97.domain.model.Comment
import com.anggit97.domain.model.Post
import com.anggit97.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
class PostRepositoryImpl(
    private val remote: AlbuminApiService,
) : PostRepository {

    override suspend fun getPosts(): Flow<List<Post>> {
        return flow {
            val result = remote.getPosts().toPostList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPostComment(postId: String): Flow<List<Comment>> {
        return flow {
            val result = remote.getPostComment(postId).toCommentList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
