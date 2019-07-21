package co.uk.jbarat.coroutinelistanddetail.domain.gateway.author

interface AuthorGateway {
    suspend fun getAuthor(id:Int):AuthorEntity
}
