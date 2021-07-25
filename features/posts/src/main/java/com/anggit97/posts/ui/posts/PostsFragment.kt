package com.anggit97.posts.ui.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.core.ext.animateGone
import com.anggit97.core.ext.animateVisible
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.autoCleared
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.ContentPostsBinding
import com.anggit97.posts.databinding.FragmentPostsBinding
import com.anggit97.posts.ui.viewmodels.PostListState
import com.anggit97.posts.ui.viewmodels.PostSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import timber.log.Timber

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private var binding: FragmentPostsBinding by autoCleared()

    private val viewModels: PostSharedViewModel by activityViewModels()

    private lateinit var listAdapter: PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPostsBinding.bind(view).apply {
            fetchData()
            contentPosts.setupView(viewModels)
        }
    }

    private fun fetchData() {
        viewModels.getPosts()
    }

    private fun ContentPostsBinding.setupView(viewModel: PostSharedViewModel) {
        listAdapter = PostsAdapter(root.context) {
            requireActivity().showToast(it.title)
        }

        rvPost.apply {
            setItemViewCacheSize(20)
            adapter = listAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = FadeInAnimator()
        }

        viewModel.postState.observe(viewLifecycleOwner) {
            it?.let { handleContentState(it) }
        }
    }

    private fun handleContentState(state: PostListState) {
        when (state) {
            is PostListState.Error -> binding.contentPosts.showErrorContent()
            is PostListState.HideLoading -> binding.contentPosts.handleLoading(false)
            is PostListState.ShowLoading -> binding.contentPosts.handleLoading(true)
            is PostListState.Success -> {
                Timber.d("Judul : " + state.data.first().title)
                binding.contentPosts.showSuccessContent(state.data)
            }
        }
    }

    private fun ContentPostsBinding.showErrorContent() {
        rvPost.animateGone(true)
        viewEmpty.root.animateGone(true)
        viewErrorConnection.root.animateVisible(true)
        viewLoading.root.animateGone(true)
    }

    private fun ContentPostsBinding.handleLoading(isLoading: Boolean) {
        rvPost.animateGone(true)
        viewEmpty.root.animateGone(true)
        viewErrorConnection.root.animateGone(true)
        viewLoading.root.animateVisible(isLoading)
    }

    private fun ContentPostsBinding.showSuccessContent(data: List<Post>) {
        rvPost.animateVisible(true)
        viewEmpty.root.animateGone(true)
        viewErrorConnection.root.animateGone(true)
        viewLoading.root.animateGone(true)

        listAdapter.submitList(data)
    }
}