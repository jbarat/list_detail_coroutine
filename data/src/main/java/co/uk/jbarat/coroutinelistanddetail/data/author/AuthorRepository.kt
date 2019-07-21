package co.uk.jbarat.coroutinelistanddetail.data.author

import co.uk.jbarat.coroutinelistanddetail.data.NetworkException
import co.uk.jbarat.coroutinelistanddetail.data.network.JsonPlaceHolderService
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorGateway

class AuthorRepository(
    private val jsonPlaceHolderService: JsonPlaceHolderService
) : AuthorGateway {
    override suspend fun getAuthor(id: Int): AuthorEntity {
        val result = jsonPlaceHolderService.getUsers(id)

        if (result.isSuccessful) {
            result.body()?.let { author ->
                return AuthorEntity(
                    id = author.id,
                    name = author.name
                )

            } ?: throw NetworkException("No body")
        } else {
            throw NetworkException("Network Error")
        }
    }
}