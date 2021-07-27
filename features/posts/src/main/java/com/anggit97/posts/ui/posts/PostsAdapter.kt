package com.anggit97.posts.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ext.getColorCompat
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
    private val listener: (Post, Array<Pair<View, String>>) -> Unit
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(diffCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    class PostViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val usernameView = binding.tvUserName
        val companyView = binding.tvUserCompany
        val avatarView = binding.ivUserAvatar
        val titleView = binding.tvPostTitle
        val bodyView = binding.tvPostBody

        fun bind(item: Post?) {
            binding.apply {
                usernameView.text = item?.user?.name
                companyView.text =
                    root.context.getString(R.string.working_at, item?.user?.company)
                titleView.text = item?.title
                bodyView.text = item?.body
                avatarView.loadAsyncCircle(item?.user?.getAvatarUrl())


                container.setview()
            }
        }

        private fun CardView.setview() {
            val even = bindingAdapterPosition.rem(2) == 0
            if (even) {
                setCardBackgroundColor(context.getColorCompat(R.color.malibu))
            } else {
                setCardBackgroundColor(context.getColorCompat(R.color.indigo200))
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
                    ViewCompat.setTransitionName(usernameView, post.getSharedElementUsernameId())
                    ViewCompat.setTransitionName(avatarView, post.getSharedElementAvatarId())
                    ViewCompat.setTransitionName(companyView, post.getSharedElementCompanyId())
                    ViewCompat.setTransitionName(titleView, post.getSharedElementTitleId())
                    ViewCompat.setTransitionName(bodyView, post.getSharedElementBodyId())
                    listener(post, createSharedElements(post))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { (holder as? PostViewHolder)?.bind(item = it) }
    }

    private fun PostViewHolder.createSharedElements(post: Post): Array<Pair<View, String>> {
        itemView.run {
            val sharedElements = mutableListOf(
                usernameView to post.getSharedElementUsernameId(),
                companyView to post.getSharedElementCompanyId(),
                avatarView to post.getSharedElementAvatarId(),
                titleView to post.getSharedElementTitleId(),
                bodyView to post.getSharedElementBodyId()
            )
            return sharedElements.toTypedArray()
        }
    }
}
