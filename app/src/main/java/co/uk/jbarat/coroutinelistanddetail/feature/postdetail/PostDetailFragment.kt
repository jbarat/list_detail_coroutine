package co.uk.jbarat.coroutinelistanddetail.feature.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import co.uk.jbarat.coroutinelistanddetail.R
import co.uk.jbarat.coroutinelistanddetail.common.ext.show
import co.uk.jbarat.coroutinelistanddetail.common.ui.BaseFragment
import co.uk.jbarat.coroutinelistanddetail.data.network.avatarServiceUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.post_detail_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostDetailFragment : BaseFragment() {

    private val args: PostDetailFragmentArgs by navArgs()

    override val viewModel: PostDetailViewModel by viewModel { parametersOf(args) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.post_detail_fragment, container, false).apply {
        toolbar.setNavigationOnClickListener { viewModel.userAction(PostDetailViewUserAction.Back) }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            loading.show(it.loading)
            content.show(it.post != null)
            error.show(it.error != null)

            error.text = it.error

            it.post?.let { post ->
                title.text = post.title
                author.text = post.authorName
                body.text = post.body
                commentCount.text = post.commentCount.toString()

                Glide.with(context).load(avatarServiceUrl + post.authorName)
                    .centerCrop()
                    .placeholder(R.drawable.ic_person)
                    .transform(RoundedCorners(8))
                    .into(avatar)
            }
        })
    }
}
