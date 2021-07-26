package com.anggit97.domain.usecase

import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by Anggit Prayogo on 26,July,2021
 * GitHub : https://github.com/anggit97
 */
interface UserUseCase {
    suspend fun getUserAlbums(userId: String): Flow<List<Album>>
    suspend fun getAlbumPhotos(albumId: String): Flow<List<Photo>>
}

class UserUseCaseImpl(
    private val userRepository: UserRepository
) : UserUseCase {

    override suspend fun getUserAlbums(userId: String): Flow<List<Album>> {
        return userRepository.getUserAlbums(userId)
    }

    override suspend fun getAlbumPhotos(albumId: String): Flow<List<Photo>> {
        return userRepository.getAlbumPhotos(albumId)
    }
}