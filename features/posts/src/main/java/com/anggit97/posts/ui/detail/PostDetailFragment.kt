package com.anggit97.posts.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.ext.setGone
import com.anggit97.core.ext.setVisible
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Comment
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.ContentDetailPostBinding
import com.anggit97.posts.databinding.FragmentPostDetailBinding
import com.anggit97.posts.databinding.HeaderDetailPostBinding
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator

@AndroidEntryPoint
class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private var binding: FragmentPostDetailBinding by autoCleared()

    private val args: PostDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<PostDetailViewModel>()

    private lateinit var commentAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = true
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailBinding.bind(view).apply {
            fetchData()
            contentDetailPost.setupView(args.post)
            contentDetailPost.setupComments(viewModel)
            headerDetailPost.setupView()
        }
    }

    private fun fetchData() {
        viewModel.getPostComment(args.post.id.toString())
    }

    private fun HeaderDetailPostBinding.setupView() {
        searchBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun ContentDetailPostBinding.setupView(post: Post) {
        tvUserName.text = post.user.name
        tvUserCompany.text =
            root.context.getString(R.string.working_at, post.user.company)
        tvPostTitle.text = post.title
        tvPostBody.text = post.body
        ivUserAvatar.loadAsyncCircle(post.user.getAvatarUrl())

        tvUserName.transitionName = post.getSharedElementUsernameId()
        tvUserCompany.transitionName = post.getSharedElementCompanyId()
        ivUserAvatar.transitionName = post.getSharedElementAvatarId()
        tvPostTitle.transitionName = post.getSharedElementTitleId()
        tvPostBody.transitionName = post.getSharedElementBodyId()

        tvUserName.setOnDebounceClickListener {
            navigateToDetailUser()
        }

        tvUserCompany.setOnDebounceClickListener {
            navigateToDetailUser()
        }

        ivUserAvatar.setOnDebounceClickListener {
            navigateToDetailUser()
        }
    }

    private fun navigateToDetailUser() {
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 500L
        }
        findNavController().navigate(PostDetailFragmentDirections.actionToDetailUser(args.post.user))
    }

    private fun ContentDetailPostBinding.setupComments(viewModel: PostDetailViewModel) {
        commentAdapter = CommentsAdapter(root.context) {
            requireActivity().showToast(it.name)
        }

        rvComments.apply {
            setItemViewCacheSize(20)
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = FadeInAnimator().apply {
                addDuration = 1000
            }
            addItemDecoration(DividerItemDecoration(root.context, LinearLayoutManager.VERTICAL))
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPostComment(args.post.id.toString())
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.commentState.observe(viewLifecycleOwner) {
            it?.let { handleCommentState(it) }
        }
    }

    private fun handleCommentState(state: PostCommentState) {
        when (state) {
            is PostCommentState.Error -> binding.contentDetailPost.showErrorContent()
            is PostCommentState.ShowLoading -> binding.contentDetailPost.handleLoading(true)
            is PostCommentState.Success -> {
                binding.contentDetailPost.tvCommentHeader.text =
                    getString(R.string.comments_count, state.data.size.toString())
                binding.contentDetailPost.showSuccessContent(state.data)
            }
        }
    }

    private fun ContentDetailPostBinding.showErrorContent() {
        rvComments.setGone()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setVisible()
        viewLoading.root.setGone()
    }

    private fun ContentDetailPostBinding.handleLoading(isLoading: Boolean) {
        if (commentAdapter.itemCount != 0 || viewModel.value == 1) return
        viewLoading.root.isVisible = isLoading
    }

    private fun ContentDetailPostBinding.showSuccessContent(data: List<Comment>) {
        rvComments.setVisible()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setGone()
        viewLoading.root.setGone()

        commentAdapter.submitList(data)
    }
}