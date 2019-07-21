package co.uk.jbarat.coroutinelistanddetail.feature.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uk.jbarat.coroutinelistanddetail.common.ext.toPresentation
import co.uk.jbarat.coroutinelistanddetail.common.navigation.NavigationCommand
import co.uk.jbarat.coroutinelistanddetail.common.ui.BaseViewModel
import co.uk.jbarat.coroutinelistanddetail.domain.getpost.GetPostParams
import co.uk.jbarat.coroutinelistanddetail.domain.getpost.GetPostResult
import co.uk.jbarat.coroutinelistanddetail.domain.getpost.GetPostUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class PostDetailViewModel(
    private val postId: Int,
    private val getPostUseCase: GetPostUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<PostDetailState>()
    val state: LiveData<PostDetailState> = _state

    private var job: Job? = null

    init {
        getPost()
    }

    fun userAction(action: PostDetailViewUserAction) {
        when (action) {
            PostDetailViewUserAction.Back -> mutableNavigation.postValue(NavigationCommand.Back)
        }
    }

    private fun getPost() {
        //cancel job if one is already running
        job?.cancel()

        job = viewModelScope.async {
            _state.postValue(PostDetailState(loading = true))
            val result = getPostUseCase.invoke(GetPostParams(postId))
            _state.postValue(handleStateUpdate(result))
        }
    }

    private fun handleStateUpdate(result: GetPostResult): PostDetailState {
        return when (result) {
            is GetPostResult.GetPostSuccess -> PostDetailState(
                post = result.simplePostEntities.toPresentation()
            )
            is GetPostResult.GetPostError -> PostDetailState(
                error = result.exception.toPresentation()
            )
        }
    }
}

/** Actions from the view */
sealed class PostDetailViewUserAction {
    object Back : PostDetailViewUserAction()
}

/** State for the view */
data class PostDetailState(
    val loading: Boolean = false,
    val post: DetailedPost? = null,
    val error: String? = null
)
