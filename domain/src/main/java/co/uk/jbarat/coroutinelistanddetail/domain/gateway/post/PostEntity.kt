package co.uk.jbarat.coroutinelistanddetail.domain.gateway.post

data class PostEntity(
        val id: Int,
        val title: String,
        val body: String,
        val authorId: Int
)
