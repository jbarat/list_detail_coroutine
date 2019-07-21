package co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment

interface CommentGateway {
    suspend fun getComments(postId: Int): List<CommentEntity>
}