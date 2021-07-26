package com.anggit97.users.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.ext.setGone
import com.anggit97.core.ext.setVisible
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.users.R
import com.anggit97.users.databinding.ContentDetailUserBinding
import com.anggit97.users.databinding.FragmentUserDetailBinding
import com.anggit97.users.databinding.HeaderDetailUserBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import timber.log.Timber

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private var binding: FragmentUserDetailBinding by autoCleared()

    private val args: UserDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<UserDetailViewModel>()

    private lateinit var albumAdapter: AlbumAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view).apply {
            headerDetailUser.setupHeader()
            contentDetailUser.setupView(viewModel)
            fetchData()
        }
    }

    private fun fetchData() {
        viewModel.getUserAlbums(args.user.id.toString())
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

    private fun ContentDetailUserBinding.setupView(viewModel: UserDetailViewModel) {
        albumAdapter = AlbumAdapter(root.context) {
            requireActivity().showToast(it.title)
        }

        rvAlbum.apply {
            setItemViewCacheSize(20)
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = FadeInAnimator().apply {
                addDuration = 1000
            }
            isNestedScrollingEnabled = false
        }

        viewModel.album.observe(viewLifecycleOwner) {
            it?.let { handleAlbumState(it) }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            it?.let { handlePhotoState(it) }
        }
    }

    private fun handlePhotoState(photos: List<Photo>) {
        val result = viewModel.getUpdateList(photos, albumAdapter.currentList)
        albumAdapter.submitList(result)

//        when (state) {
//            is PhotosState.Success -> {
//                val result = viewModel.getUpdateList(state.data, albumAdapter.currentList)
//                albumAdapter.submitList(result)
//            }
//            else -> {}
//        }
    }

    private fun handleAlbumState(state: AlbumState) {
        when (state) {
            is AlbumState.Error -> binding.contentDetailUser.showErrorContent()
            is AlbumState.HideLoading -> binding.contentDetailUser.handleLoading(false)
            is AlbumState.ShowLoading -> binding.contentDetailUser.handleLoading(true)
            is AlbumState.Success -> {
                viewModel.getAlbumPhotos(state.data)
                binding.contentDetailUser.showSuccessContent(state.data)
            }
        }
    }

    private fun ContentDetailUserBinding.showErrorContent() {
        rvAlbum.setGone()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setGone()
        viewLoading.root.setVisible()
    }

    private fun ContentDetailUserBinding.handleLoading(isLoading: Boolean) {
        if (albumAdapter.itemCount != 0) return
        viewLoading.root.isVisible = isLoading
    }

    private fun ContentDetailUserBinding.showSuccessContent(data: List<Album>) {
        rvAlbum.setVisible()
        viewEmpty.root.setGone()
        viewErrorConnection.root.setGone()
        viewLoading.root.setGone()

        albumAdapter.submitList(data)
    }
}