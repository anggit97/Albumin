package com.anggit97.users.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 26,July,2021
 * GitHub : https://github.com/anggit97
 */
interface UserDetailViewModelContract {
    fun getUserAlbums(userId: String)
    fun getAlbumPhotos(albums: List<Album>)
}

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel(), UserDetailViewModelContract {

    private val _album = MutableLiveData<AlbumState>()
    val album: LiveData<AlbumState>
        get() = _album

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

//    val photos: LiveData<PhotosState> = _album.switchMap { albumState ->
//        liveData {
//            if (albumState is AlbumState.Success) {
//                albumState.data.map {
//                    userUseCase.getAlbumPhotos(it.id.toString())
//                        .catch { throwable -> emit(PhotosState.Error(throwable)) }
//                        .onStart { emit(PhotosState.ShowLoading) }
//                        .onCompletion { emit(PhotosState.HideLoading) }
//                        .collect { result ->
//                            emit(PhotosState.Success(result))
//                        }
//                }
//            }
//        }
//    }

    override fun getUserAlbums(userId: String) {
        viewModelScope.launch {
            userUseCase.getUserAlbums(userId)
                .catch { _album.value = AlbumState.Error(it) }
                .onStart { _album.value = AlbumState.ShowLoading }
                .onCompletion { _album.value = AlbumState.HideLoading }
                .collectLatest {
                    _album.value = AlbumState.Success(it)
                }
        }
    }

    override fun getAlbumPhotos(albums: List<Album>) {
        viewModelScope.launch {
            albums.map {
                userUseCase.getAlbumPhotos(it.id.toString())
                    .collect { result ->
                        _photos.value = result
                    }
            }
        }
    }

    fun getUpdateList(photos: List<Photo>, albums: List<Album>): List<Album> {
        val albumId = photos.firstOrNull()?.albumId
        return if (albumId == null) {
            albums
        } else {
            albums
                .map { album ->
                    if (album.id == albumId) {
                        val albumCopy = album.copy()
                        albumCopy.photos = photos
                        albumCopy
                    } else {
                        album
                    }
                }
        }
    }
}

sealed class AlbumState {
    data class Success(val data: List<Album>) : AlbumState()
    data class Error(val error: Throwable) : AlbumState()
    object ShowLoading : AlbumState()
    object HideLoading : AlbumState()
}

sealed class PhotosState {
    data class Success(val data: List<Photo>) : PhotosState()
    data class Error(val error: Throwable) : PhotosState()
    object ShowLoading : PhotosState()
    object HideLoading : PhotosState()
}
