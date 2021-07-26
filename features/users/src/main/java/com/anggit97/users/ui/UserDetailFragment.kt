package com.anggit97.users.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anggit97.core.util.autoCleared
import com.anggit97.users.R
import com.anggit97.users.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private var binding: FragmentUserDetailBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view)
    }
}