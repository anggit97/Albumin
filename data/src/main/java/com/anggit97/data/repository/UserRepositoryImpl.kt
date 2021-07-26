package com.anggit97.data.repository

import com.anggit97.data.api.AlbuminApiService
import com.anggit97.data.repository.mapper.toAlbumList
import com.anggit97.data.repository.mapper.toPhotoList
import com.anggit97.data.repository.mapper.toUserList
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.domain.model.User
import com.anggit97.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
class UserRepositoryImpl(
    private val remote: AlbuminApiService
) : UserRepository {

    override suspend fun getUsers(): Flow<List<User>> {
        return flow {
            val result = remote.getUsers().toUserList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserAlbums(userId: String): Flow<List<Album>> {
        return flow {
            val result = remote.getUserAlbums(userId).toAlbumList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAlbumPhotos(albumId: String): Flow<List<Photo>> {
        return flow {
            val result = remote.getAlbumPhotos(albumId).toPhotoList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}