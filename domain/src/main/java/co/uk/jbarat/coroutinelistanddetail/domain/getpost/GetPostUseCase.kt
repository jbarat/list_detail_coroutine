package co.uk.jbarat.coroutinelistanddetail.domain.getpost

import co.uk.jbarat.coroutinelistanddetail.domain.common.UseCase
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostGateway
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetPostUseCase(
        private val postGateway: PostGateway,
        private val authorGateway: AuthorGateway,
        private val commentGateway: CommentGateway
) : UseCase<GetPostParams, GetPostResult> {

    override suspend fun invoke(params: GetPostParams): GetPostResult {
        return try {
            coroutineScope {
                val post = async { postGateway.getPost(params.postId) }
                val author = async { authorGateway.getAuthor(post.await().authorId) }
                val comments = async { commentGateway.getComments(params.postId) }

                getPostSuccess(post.await(), comments.await(), author.await())
            }
        } catch (e: Exception) {
            GetPostResult.GetPostError(e)
        }
    }

    private fun getPostSuccess(
            post: PostEntity,
            comments: List<CommentEntity>,
            author: AuthorEntity
    ): GetPostResult.GetPostSuccess {
        return GetPostResult.GetPostSuccess(
                DetailedPostEntity(
                        id = post.id,
                        body = post.body,
                        title = post.title,
                        author = author,
                        commentEntities = comments
                )
        )
    }
}