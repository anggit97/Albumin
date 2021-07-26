package com.anggit97.users.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.users.R
import com.anggit97.users.databinding.FragmentUserDetailBinding
import com.anggit97.users.databinding.HeaderDetailUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private var binding: FragmentUserDetailBinding by autoCleared()

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view).apply {
            headerDetailUser.setupHeader()
        }
    }

    private fun HeaderDetailUserBinding.setupHeader() {
        val user = args.user
        tvUserName.text = user.name
        tvUserCompany.text = root.context.getString(R.string.working_at, user.company)
        tvUserEmail.text = user.email
        tvUserLocation.text = user.address
        ivUserAvatar.loadAsyncCircle(user.getAvatarUrl())

        searchBack.setOnDebounceClickListener {
            findNavController().navigateUp()
        }
    }
}