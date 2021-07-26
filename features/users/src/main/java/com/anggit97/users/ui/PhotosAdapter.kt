package com.anggit97.users.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.loadAsync
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Photo
import com.anggit97.users.databinding.PhotoItemBinding
import timber.log.Timber

/**
 * Created by Anggit Prayogo on 26,July,2021
 * GitHub : https://github.com/anggit97
 */
class PhotosAdapter(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<Photo> = IdBasedDiffCallback { it.id.toString() },
    private val listener: (Photo) -> Unit
) : ListAdapter<Photo, PhotosAdapter.PostViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    class PostViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo?) {
            binding.apply {
                Timber.d("PHOTO URL : ${item?.thumbnailUrl}")
                ivPhoto.loadAsync(item?.thumbnailUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PhotoItemBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding).apply {
            itemView.setOnDebounceClickListener(delay = 150L) {
                val index = bindingAdapterPosition
                if (index in 0..itemCount) {
                    val photo: Photo = getItem(index)
                    listener(photo)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { (holder as? PostViewHolder)?.bind(item = it) }
    }
}
