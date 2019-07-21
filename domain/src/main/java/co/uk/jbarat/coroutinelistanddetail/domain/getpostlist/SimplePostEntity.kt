package co.uk.jbarat.coroutinelistanddetail.domain.getpostlist

import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity

data class SimplePostEntity(
        val id: Int,
        val title: String,
        val body: String,
        val author: AuthorEntity
)