package com.anggit97.posts.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Comment
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.CommentItemBinding
import com.anggit97.posts.databinding.PostItemBinding


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
class CommentsAdapter(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<Comment> = IdBasedDiffCallback { it.id.toString() },
    private val listener: (Comment) -> Unit
) : ListAdapter<Comment, CommentsAdapter.PostViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    class PostViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment?) {
            binding.apply {
                tvAuthor.text = item?.name
                ivUserAvatar.loadAsyncCircle(item?.getAvatarUrl())
                tvComment.text = item?.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CommentItemBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding).apply {
            itemView.setOnDebounceClickListener(delay = 150L) {
                val index = bindingAdapterPosition
                if (index in 0..itemCount) {
                    val comment: Comment = getItem(index)
                    listener(comment)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { (holder as? PostViewHolder)?.bind(item = it) }
    }
}
