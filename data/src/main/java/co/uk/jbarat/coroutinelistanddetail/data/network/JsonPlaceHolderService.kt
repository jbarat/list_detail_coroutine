package co.uk.jbarat.coroutinelistanddetail.data.network

import co.uk.jbarat.coroutinelistanddetail.data.author.AuthorModel
import co.uk.jbarat.coroutinelistanddetail.data.comment.CommentModel
import co.uk.jbarat.coroutinelistanddetail.data.post.PostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceHolderService {

    @GET("/users/{id}")
    suspend fun getUsers(@Path("id") userId: Int): Response<AuthorModel>

    @GET("/posts")
    suspend fun getPosts(): Response<List<PostModel>>

    @GET("/posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Response<PostModel>

    @GET("/comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<CommentModel>>
}