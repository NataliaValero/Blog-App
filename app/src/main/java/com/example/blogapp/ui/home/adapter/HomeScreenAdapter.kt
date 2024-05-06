package com.example.blogapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blogapp.R
import com.example.blogapp.core.BaseViewHolder
import com.example.blogapp.core.hide
import com.example.blogapp.data.model.Post
import com.example.blogapp.databinding.PostItemViewBinding
import com.example.blogapp.core.DateFormatter

class HomeScreenAdapter(
    private var post: List<Post>,
    private val onPostClickListener: OnPostClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postClickListener: OnPostClickListener? = null


    init {
        postClickListener = onPostClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(post[position])
        }
    }

    override fun getItemCount(): Int {
        return post.size
    }

    fun setNewList(newList: List<Post>) {
        this.post = newList
        notifyDataSetChanged()
    }




    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding, val context: Context
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {


            setUpProfileInfo(item)
            addTimeStamp(item)
            setUpPostImage(item)
            setUpPostDescription(item)
            tinHeartIcon(item)
            setUpLikeCount(item.likes)
            setLikeClickAction(item)

        }


        private fun setUpProfileInfo(post: Post) {

            post.poster?.let {

                binding.profileName.text = it.profileName

                Glide.with(context)
                    .load(it.profilePicture)
                    .into(binding.profilePicture)
            }
        }

        private fun addTimeStamp(post: Post) {
            binding.postTimestamp.text = post.createdAt?.let { DateFormatter.getTimeAgo(it) }
        }

        private fun setUpPostImage(post: Post) {
            Glide.with(context)
                .load(post.postImage)
                .into(binding.postImage)
        }


        private fun setUpPostDescription(post: Post) {
            // Hide description if user does not input any description
            if (post.postDescription.isEmpty()) {
                binding.postDescription.hide()
            } else {
                binding.postDescription.text = post.postDescription
            }
        }

        private fun tinHeartIcon(post: Post) {
            if (!post.liked) {
                binding.likeButton.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_outlined))

            } else {
                binding.likeButton.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_filled))
            }
        }

        private fun setUpLikeCount(likes: Long) {


            if (likes == 1L) {
                binding.likesCount.text = "$likes like"
            } else {
                binding.likesCount.text = "$likes likes"
            }
        }


        private fun setLikeClickAction(post: Post) {

            var likes = post.likes
            binding.likeButton.setOnClickListener {

                //  If post.liked is currently true, it will be set to false, and vice versa.
                post.liked = !post.liked
                // Tint heart with new state of liked
                tinHeartIcon(post)

                // Use interface with event to update UI
                postClickListener?.onLikeButtonClick(post, post.liked)


                // Update likes count
                if (post.liked) likes += 1 else {
                    likes -= 1
                }
                // Update likes count
                setUpLikeCount(likes)

            }

        }

    }


    interface OnPostClickListener {

        fun onLikeButtonClick(post: Post, isLiked: Boolean)
    }

}