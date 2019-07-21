package co.uk.jbarat.coroutinelistanddetail.common.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.uk.jbarat.coroutinelistanddetail.common.navigation.NavigationCommand
import co.uk.jbarat.coroutinelistanddetail.feature.postlist.PostListFragmentDirections

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer { navigate(it) })
    }

    private fun navigate(it: NavigationCommand) {
        when (it) {
            NavigationCommand.Back -> findNavController().popBackStack()
            is NavigationCommand.PostDetails -> findNavController()
                .navigate(PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(it.postId))
        }
    }
}