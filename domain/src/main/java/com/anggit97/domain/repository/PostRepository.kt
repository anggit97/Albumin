package com.anggit97.domain.repository

import com.anggit97.domain.model.Post
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
interface PostRepository {

    suspend fun getPosts(): Flow<List<Post>>
}