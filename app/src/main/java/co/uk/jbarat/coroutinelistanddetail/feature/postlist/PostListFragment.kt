package co.uk.jbarat.coroutinelistanddetail.feature.postlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.jbarat.coroutinelistanddetail.R
import co.uk.jbarat.coroutinelistanddetail.common.ext.show
import co.uk.jbarat.coroutinelistanddetail.common.ui.BaseFragment
import kotlinx.android.synthetic.main.post_list_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment : BaseFragment() {

    override val viewModel: PostListViewModel by viewModel()

    private val postListAdapter = PostListAdapter(clickCallback = {
        viewModel.userAction(PostListViewUserAction.PostSelected(it))
    })

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.post_list_fragment, container, false).apply {
        posts.apply {
            adapter = postListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        swipe_refresh.setOnRefreshListener { viewModel.userAction(PostListViewUserAction.Refresh) }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            posts.show(it.postEntities != null)
            error.show(it.error != null)

            error.text = it.error

            it.postEntities?.let { posts ->
                postListAdapter.updateData(posts)
            }

            swipe_refresh.isRefreshing = it.loading
        })
    }
}
