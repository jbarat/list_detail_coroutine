package co.uk.jbarat.coroutinelistanddetail.feature.postlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.uk.jbarat.coroutinelistanddetail.R
import co.uk.jbarat.coroutinelistanddetail.common.ext.getDimensionInPixel
import co.uk.jbarat.coroutinelistanddetail.data.network.avatarServiceUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostListAdapter(
    private val clickCallback: (selectedPost: SimplePost) -> Unit
) : RecyclerView.Adapter<PostListAdapter.PostListViewHolder>() {

    private var posts = emptyList<SimplePost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).run {
            PostListViewHolder(inflate(R.layout.post_list_item, parent, false), clickCallback::invoke)
        }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) = holder.bind(posts[position])

    fun updateData(posts: List<SimplePost>) {
        val diffResult = DiffUtil.calculateDiff(PostDiffUtilCallback(this.posts, posts))
        this.posts = posts
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PostListViewHolder(
        itemView: View,
        clickCallback: (selectedPost: SimplePost) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var post: SimplePost? = null

        init {
            itemView.setOnClickListener {
                post?.let(clickCallback::invoke)
            }
        }

        fun bind(post: SimplePost) {
            this.post = post

            itemView.apply {
                title.text = post.title
                author.text = post.author

                Glide.with(context).load(avatarServiceUrl + post.author)
                    .centerCrop()
                    .placeholder(R.drawable.ic_person)
                    .transform(RoundedCorners(resources.getDimensionInPixel(R.dimen.avatar_corner)))
                    .into(avatar)
            }
        }
    }
}

