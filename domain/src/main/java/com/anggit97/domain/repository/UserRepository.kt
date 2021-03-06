package com.anggit97.domain.repository

import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.domain.model.User
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
interface UserRepository {

    suspend fun getUsers(): Flow<List<User>>

    suspend fun getUserAlbums(userId: String): Flow<List<Album>>

    suspend fun getAlbumPhotos(albumId: String): Flow<List<Photo>>
}