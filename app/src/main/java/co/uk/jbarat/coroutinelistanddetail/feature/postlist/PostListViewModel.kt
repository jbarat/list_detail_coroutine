package co.uk.jbarat.coroutinelistanddetail.feature.postlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uk.jbarat.coroutinelistanddetail.common.ext.exhaustive
import co.uk.jbarat.coroutinelistanddetail.common.ext.toPresentation
import co.uk.jbarat.coroutinelistanddetail.common.navigation.NavigationCommand
import co.uk.jbarat.coroutinelistanddetail.common.ui.BaseViewModel
import co.uk.jbarat.coroutinelistanddetail.domain.getpostlist.GetPostListResult
import co.uk.jbarat.coroutinelistanddetail.domain.getpostlist.GetPostListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class PostListViewModel(
    private val postListUseCase: GetPostListUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<PostListState>()
    val state: LiveData<PostListState> = _state

    private var job: Job? = null

    init {
        getPosts()
    }

    fun userAction(userAction: PostListViewUserAction) {
        when (userAction) {
            is PostListViewUserAction.PostSelected -> {
                mutableNavigation.postValue(NavigationCommand.PostDetails(userAction.post.id))
            }
            PostListViewUserAction.Refresh -> {
                getPosts()
            }
        }.exhaustive
    }

    private fun getPosts() {
        //cancel job if one is already running
        job?.cancel()

        job = viewModelScope.async {
            _state.postValue(PostListState(loading = true))
            val result = postListUseCase.invoke(this)
            _state.postValue(handleStateUpdate(result))
        }
    }

    private fun handleStateUpdate(result: GetPostListResult): PostListState {
        return when (result) {
            is GetPostListResult.GetPostListSuccess -> PostListState(
                postEntities = result.simplePostEntities.map { it.toPresentation() })
            is GetPostListResult.GetPostListError -> PostListState(
                error = result.exception.toPresentation()
            )
        }
    }
}

/** Actions from the view */
sealed class PostListViewUserAction {
    data class PostSelected(val post: SimplePost) : PostListViewUserAction()
    object Refresh : PostListViewUserAction()
}

/** State for the view */
data class PostListState(
    val loading: Boolean = false,
    val postEntities: List<SimplePost>? = null,
    val error: String? = null
)
