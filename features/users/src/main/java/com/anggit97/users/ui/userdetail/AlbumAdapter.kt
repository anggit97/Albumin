package com.anggit97.users.ui.userdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Album
import com.anggit97.domain.model.Photo
import com.anggit97.users.databinding.AlbumItemBinding
import jp.wasabeef.recyclerview.animators.FadeInAnimator

/**
 * Created by Anggit Prayogo on 26,July,2021
 * GitHub : https://github.com/anggit97
 */
class AlbumAdapter(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<Album> = IdBasedDiffCallback { it.id.toString() },
    private val listener: (Album) -> Unit,
    private val listenerPhoto: (Photo) -> Unit
) : ListAdapter<Album, AlbumAdapter.PostViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    class PostViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album?, listenerPhoto: (Photo) -> Unit) {
            binding.apply {
                tvAlbumTitle.text = item?.title

                val photoAdapter = PhotosAdapter(root.context) {
                    listenerPhoto(it)
                }

                rvPhotos.apply {
                    adapter = photoAdapter
                    itemAnimator = FadeInAnimator().apply {
                        addDuration = 1000
                    }
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = false
                }

                photoAdapter.submitList(item?.photos?.take(20))
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
        getItem(position)?.let { (holder as? PostViewHolder)?.bind(item = it, listenerPhoto) }
    }
}