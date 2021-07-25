package com.anggit97.posts.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.util.IdBasedDiffCallback
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.domain.model.Post
import com.anggit97.posts.R
import com.anggit97.posts.databinding.PostItemBinding


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
class PostsAdapter(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<Post> = IdBasedDiffCallback { it.id.toString() },
//    private val listener: (Post, Array<Pair<View, String>>) -> Unit
    private val listener: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    class PostViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post?) {
            binding.apply {
                tvUserName.text = item?.user?.name
                tvUserCompany.text =
                    root.context.getString(R.string.working_at, item?.user?.company)
                tvPostTitle.text = item?.title
                tvPostBody.text = item?.body
                ivUserAvatar.loadAsyncCircle(item?.user?.getAvatarUrl())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding).apply {
            itemView.setOnDebounceClickListener(delay = 150L) {
                val index = bindingAdapterPosition
                if (index in 0..itemCount) {
                    val post: Post = getItem(index)
                    listener(post)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { (holder as? PostViewHolder)?.bind(item = it) }
    }
}
