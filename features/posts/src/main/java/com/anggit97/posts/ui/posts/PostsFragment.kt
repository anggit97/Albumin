package com.anggit97.posts.ui.posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.core.ext.*
import com.anggit97.core.util.autoCleared
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.ContentPostsBinding
import com.anggit97.posts.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import timber.log.Timber


@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private var binding: FragmentPostsBinding by autoCleared()

    private val viewModels by viewModels<PostViewModel>()

    private lateinit var listAdapter: PostsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("ON ATTACH")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("ONCREATE")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("ONCREATE VIEW")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("ONCREATE VIEW CREATED")
        binding = FragmentPostsBinding.bind(view).apply {
            fetchData()
            contentPosts.setupView(viewModels)
        }
    }

    private fun fetchData() {
        viewModels.getPosts()
    }

    private fun ContentPostsBinding.setupView(viewModel: PostViewModel) {
        listAdapter = PostsAdapter(root.context) { post, sharedElements ->
            val navOptions: NavOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .build()

            findNavController().navigate(
                PostsFragmentDirections.actionToDetailPost(post),
                navOptions
//                FragmentNavigatorExtras(*sharedElements)
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
        if (listAdapter.itemCount != 0) return
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
        super.onDestroyView()
        Timber.d("DESTROY VIEW")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("DESTROY")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("DETACH")
    }
}