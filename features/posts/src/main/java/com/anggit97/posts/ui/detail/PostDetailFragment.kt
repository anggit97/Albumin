package com.anggit97.posts.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ChangeBounds
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.ContentDetailPostBinding
import com.anggit97.posts.databinding.FragmentPostDetailBinding
import com.anggit97.posts.databinding.HeaderDetailPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private var binding: FragmentPostDetailBinding by autoCleared()

    private val args: PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailBinding.bind(view).apply {
            contentDetailPost.setupView(args.post)
            headerDetailPost.setupView()
        }
    }

    private fun HeaderDetailPostBinding.setupView(){
        searchBackContainer.setOnClickListener {
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
    }
}