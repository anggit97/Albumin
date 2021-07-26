package com.anggit97.data.api

import com.anggit97.data.api.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
interface AlbuminApiService {

    @GET("posts")
    suspend fun getPosts(): List<PostResponse>

    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @GET("comments")
    suspend fun getPostComment(@Query("postId") postId: String): List<CommentResponse>

    @GET("users/{userId}/albums")
    suspend fun getAlbums(@Path("userId") userId: String): List<AlbumResponse>

    @GET("albums/{albumId}/photos")
    suspend fun getAlbumPhotos(@Path("albumId") albumId: String): List<PhotoResponse>
}