package co.uk.jbarat.coroutinelistanddetail.data.post

import co.uk.jbarat.coroutinelistanddetail.data.NetworkException
import co.uk.jbarat.coroutinelistanddetail.data.network.JsonPlaceHolderService
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostGateway

class PostRepository(
        private val jsonPlaceHolderService: JsonPlaceHolderService
) : PostGateway {

    override suspend fun getAllPost(): List<PostEntity> {
        val result = jsonPlaceHolderService.getPosts()

        if (result.isSuccessful) {
            result.body()?.let { posts ->
                return posts.map {
                    PostEntity(
                            id = it.id,
                            authorId = it.userId,
                            title = it.title,
                            body = it.body
                    )
                }
            } ?: throw NetworkException("No body")
        } else {
            throw NetworkException("Network Error")
        }
    }

    override suspend fun getPost(id: Int): PostEntity {
        val result = jsonPlaceHolderService.getPost(id)

        if (result.isSuccessful) {
            result.body()?.let { post ->
                return PostEntity(
                        id = post.id,
                        authorId = post.userId,
                        title = post.title,
                        body = post.body
                )
            } ?: throw NetworkException("No body")
        } else {
            throw  NetworkException("Network Error")
        }

    }
}