package com.anggit97.posts.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.domain.model.Post
import com.anggit97.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
interface PostSharedViewModelContract {
    fun getPosts()
    fun getPostDetailById(postId: String, postsList: List<Post>): Post?
}

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) : ViewModel(), PostSharedViewModelContract {

    private val _postState = MutableLiveData<PostListState>()
    val postState: LiveData<PostListState>
        get() = _postState

    var value = 0

    override fun getPosts() {
        viewModelScope.launch {
            postUseCase.getPostList()
                .catch { _postState.value = PostListState.Error(it) }
                .onStart { _postState.value = PostListState.ShowLoading }
                .collectLatest {
                    _postState.value = PostListState.Success(it)
                    value = 1
                }
        }
    }

    override fun getPostDetailById(postId: String, postsList: List<Post>): Post? {
        return postsList.firstOrNull { it.id.toString() == postId }
    }
}

sealed class PostListState {
    data class Success(val data: List<Post>) : PostListState()
    data class Error(val error: Throwable) : PostListState()
    object ShowLoading : PostListState()
}
