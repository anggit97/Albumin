package com.anggit97.posts.ui.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anggit97.core.util.autoCleared
import com.anggit97.posts.R
import com.anggit97.posts.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private var binding: FragmentPostsBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPostsBinding.bind(view)
    }
}