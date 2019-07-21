package co.uk.jbarat.coroutinelistanddetail.domain.getpost

import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentEntity

data class DetailedPostEntity(
        val id: Int,
        val title: String,
        val body: String,
        val author: AuthorEntity,
        val commentEntities: List<CommentEntity>
)

