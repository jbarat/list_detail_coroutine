package co.uk.jbarat.coroutinelistanddetail.data.comment

import co.uk.jbarat.coroutinelistanddetail.data.NetworkException
import co.uk.jbarat.coroutinelistanddetail.data.network.JsonPlaceHolderService
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentGateway

class CommentRepository(
        private val jsonPlaceHolderService: JsonPlaceHolderService
) : CommentGateway {
    override suspend fun getComments(postId: Int): List<CommentEntity> {
        val result = jsonPlaceHolderService.getComments(postId)

        if (result.isSuccessful) {
            result.body()?.let { comments ->
                return comments.map { CommentEntity(it.id) }
            } ?: throw NetworkException("No body")
        } else {
            throw NetworkException("Network Error")
        }
    }
}