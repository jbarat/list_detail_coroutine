package co.uk.jbarat.coroutinelistanddetail.domain.getpostlist

import co.uk.jbarat.coroutinelistanddetail.domain.common.NoParamUseCase
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostGateway

class GetPostListUseCase(
        private val postGateway: PostGateway,
        private val authorGateway: AuthorGateway
) : NoParamUseCase<GetPostListResult> {

    override suspend fun invoke(): GetPostListResult {
        return try {
            GetPostListResult.GetPostListSuccess(
                    postGateway.getAllPost()
                            .map { it.mapToDetailedPostEntity(authorGateway.getAuthor(it.authorId)) }
            )
        } catch (e: Exception) {
            GetPostListResult.GetPostListError(e)
        }
    }
}

private fun PostEntity.mapToDetailedPostEntity(authorEntity: AuthorEntity): SimplePostEntity {
    return SimplePostEntity(
            id = id,
            title = title,
            body = body,
            author = authorEntity
    )
}

