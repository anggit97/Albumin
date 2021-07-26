package com.anggit97.users.ui.fullscreen

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anggit97.core.glide.GlideApp
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Photo
import com.anggit97.users.R
import com.anggit97.users.databinding.ContentDetailFullscreenBinding
import com.anggit97.users.databinding.FragmentFullScreenImageBinding
import com.anggit97.users.databinding.HeaderDetailFulllscreenBinding
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenImageFragment : Fragment(R.layout.fragment_full_screen_image) {

    private var binding: FragmentFullScreenImageBinding by autoCleared()

    private val args: FullScreenImageFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = true
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFullScreenImageBinding.bind(view).apply {
            val photo = args.photo
            headerDetailFullScreenImage.setView(photo)
            contentDetailFullscreenImage.setView(photo)
        }
    }

    private fun HeaderDetailFulllscreenBinding.setView(photo: Photo) {
        tvAppBarTitle.text = photo.title

        searchBack.setOnDebounceClickListener {
            findNavController().navigateUp()
        }
    }

    private fun ContentDetailFullscreenBinding.setView(photo: Photo) {
        photoView.transitionName = photo.url

        GlideApp.with(this@FullScreenImageFragment)
            .asBitmap()
            .load(photo.url.toGlideUrl())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    photoView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }

    private fun String.toGlideUrl(): GlideUrl {
        return GlideUrl(
            this, LazyHeaders.Builder()
                .addHeader("User-Agent", "USER_AGENT")
                .build()
        )
    }
}