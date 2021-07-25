package com.anggit97.posts.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.domain.model.Comment
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
interface PostDetailViewModelContract {
    fun getPostComment(postId: String)
}

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) : ViewModel(), PostDetailViewModelContract {

    private val _commentState = MutableLiveData<PostCommentState>()
    val commentState: LiveData<PostCommentState>
        get() = _commentState

    override fun getPostComment(postId: String) {
        viewModelScope.launch {
            postUseCase.getPostComment(postId)
                .catch { _commentState.value = PostCommentState.Error(it) }
                .onStart { _commentState.value = PostCommentState.ShowLoading }
                .onCompletion { _commentState.value = PostCommentState.HideLoading }
                .collectLatest { _commentState.value = PostCommentState.Success(it) }
        }
    }
}

sealed class PostCommentState {
    data class Success(val data: List<Comment>) : PostCommentState()
    data class Error(val error: Throwable) : PostCommentState()
    object ShowLoading : PostCommentState()
    object HideLoading : PostCommentState()
}
