package co.uk.jbarat.coroutinelistanddetail.common.navigation

sealed class NavigationCommand {
    object Back : NavigationCommand()
    data class PostDetails(val postId: Int) : NavigationCommand()
}