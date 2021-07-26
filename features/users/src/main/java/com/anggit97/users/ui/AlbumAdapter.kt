package com.anggit97.users.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Album
import com.anggit97.users.databinding.AlbumItemBinding
import jp.wasabeef.recyclerview.animators.FadeInAnimator

/**
 * Created by Anggit Prayogo on 26,July,2021
 * GitHub : https://github.com/anggit97
 */
class AlbumAdapter(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<Album> = IdBasedDiffCallback { it.id.toString() },
    private val listener: (Album) -> Unit
) : ListAdapter<Album, AlbumAdapter.PostViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    class PostViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album?) {
            binding.apply {
                tvAlbumTitle.text = item?.title

                val photoAdapter = PhotosAdapter(root.context) {
                    root.context.showToast(it.title)
                }

                rvPhotos.apply {
                    setItemViewCacheSize(20)
                    adapter = photoAdapter
                    itemAnimator = FadeInAnimator().apply {
                        addDuration = 1000
                    }
                    isNestedScrollingEnabled = false
                }

                photoAdapter.submitList(item?.photos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = AlbumItemBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding).apply {
            itemView.setOnDebounceClickListener(delay = 150L) {
                val index = bindingAdapterPosition
                if (index in 0..itemCount) {
                    val album: Album = getItem(index)
                    listener(album)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { (holder as? PostViewHolder)?.bind(item = it) }
    }
}