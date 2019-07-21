package co.uk.jbarat.coroutinelistanddetail.domain.gateway.post

interface PostGateway {
    suspend fun getAllPost(): List<PostEntity>
    suspend fun getPost(id:Int): PostEntity
}
