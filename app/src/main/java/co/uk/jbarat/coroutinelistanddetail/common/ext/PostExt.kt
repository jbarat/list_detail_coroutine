package co.uk.jbarat.coroutinelistanddetail.common.ext

import co.uk.jbarat.coroutinelistanddetail.domain.getpost.DetailedPostEntity
import co.uk.jbarat.coroutinelistanddetail.domain.getpostlist.SimplePostEntity
import co.uk.jbarat.coroutinelistanddetail.feature.postdetail.DetailedPost
import co.uk.jbarat.coroutinelistanddetail.feature.postlist.SimplePost

/**Mapping from domain layer to presentation*/

fun SimplePostEntity.toPresentation() = SimplePost(id = id, author = author.name, title = title)

fun DetailedPostEntity.toPresentation() = DetailedPost(
        title = title,
        authorName = author.name,
        body = body,
        commentCount = commentEntities.size
)

