package com.anggit97.posts.ui.posts

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.core.ext.*
import com.anggit97.core.util.autoCleared
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.ContentPostsBinding
import com.anggit97.posts.databinding.FragmentPostsBinding
import com.anggit97.posts.ui.detail.PostDetailFragmentArgs
import com.anggit97.posts.ui.viewmodels.PostListState
import com.anggit97.posts.ui.viewmodels.PostSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import jp.wasabeef.recyclerview.animators.LandingAnimator

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
        listAdapter = PostsAdapter(root.context) { post, sharedElements ->
            findNavController().navigate(
                PostsFragmentDirections.actionToDetailPost(post),
                ActivityNavigatorExtras(
                    activityOptions = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(requireActivity(), *sharedElements)
                )
            )
        }

        rvPost.apply {
            setItemViewCacheSize(20)
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = FadeInAnimator().apply {
                addDuration = 1000
            }
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
                binding.contentPosts.showSuccessContent(state.data)
            }
        }
    }

    private fun ContentPostsBinding.showErrorContent() {
        rvPost.setGone()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setGone()
        viewLoading.root.setVisible()
    }

    private fun ContentPostsBinding.handleLoading(isLoading: Boolean) {
        viewLoading.root.isVisible = isLoading
    }

    private fun ContentPostsBinding.showSuccessContent(data: List<Post>) {
        rvPost.setVisible()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setGone()
        viewLoading.root.setGone()

        listAdapter.submitList(data)
    }
}