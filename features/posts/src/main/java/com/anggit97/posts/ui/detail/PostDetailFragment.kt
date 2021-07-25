package com.anggit97.posts.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anggit97.core.util.autoCleared
import com.anggit97.posts.R
import com.anggit97.posts.databinding.FragmentPostDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private var binding: FragmentPostDetailBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailBinding.bind(view)
    }
}