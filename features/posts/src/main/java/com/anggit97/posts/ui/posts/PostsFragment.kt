package com.anggit97.posts.ui.posts

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.anggit97.core.ext.setGone
import com.anggit97.core.ext.setVisible
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.ContentPostsBinding
import com.anggit97.posts.databinding.FragmentPostsBinding
import com.anggit97.posts.databinding.HeaderPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import timber.log.Timber

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!


    private val viewModels: PostViewModel by activityViewModels()

    private val listAdapter: PostsAdapter by lazy {
        PostsAdapter(binding.contentPosts.root.context) { post, sharedElements ->
            findNavController().navigate(
                PostsFragmentDirections.actionToDetailPost(post)
//                FragmentNavigatorExtras(*sharedElements)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentPostsBinding.bind(view)
        binding.apply {
            fetchData()
            contentPosts.setupView(viewModels)
        }
    }

    private fun fetchData() {
        viewModels.getPosts()
    }

    private fun ContentPostsBinding.setupView(viewModel: PostViewModel) {
        rvPost.apply {
            setItemViewCacheSize(20)
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = FadeInAnimator().apply {
                addDuration = 1000
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            fetchData()
            swipeRefreshLayout.isRefreshing = false
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
        if (listAdapter.itemCount > 0 || viewModels.value == 1) return
        viewLoading.root.isVisible = isLoading
    }

    private fun ContentPostsBinding.showSuccessContent(data: List<Post>) {
        rvPost.setVisible()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setGone()
        viewLoading.root.setGone()

        listAdapter.submitList(data)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}