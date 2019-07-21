package co.uk.jbarat.coroutinelistanddetail.domain.getpost

import co.uk.jbarat.coroutinelistanddetail.TestException
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostGateway
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPostUseCaseTest {

    @Test
    fun `when data available from every gateway then return data mapped`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithCorrectData()
        val authorGateway = givenAuthorGatewayWithCorrectData()
        val commentGateway = givenCommentGatewayWithCorrectData()
        val subject = GetPostUseCase(postGateway, authorGateway, commentGateway)

        //Act
        val result = subject.invoke(GetPostParams(postId))

        //Verify
        assertEquals(
            result,
            GetPostResult.GetPostSuccess(
                DetailedPostEntity(
                    id = postId,
                    title = testPostEntity.title,
                    body = testPostEntity.body,
                    commentEntities = testComments,
                    author = testAuthorEntity
                )
            )
        )
    }

    @Test
    fun `when postGateway throws an error then return a wrapped exception`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithException()
        val authorGateway = givenAuthorGatewayWithCorrectData()
        val commentGateway = givenCommentGatewayWithCorrectData()
        val subject = GetPostUseCase(postGateway, authorGateway, commentGateway)

        //Act
        val result = subject.invoke(GetPostParams(postId))

        //Verify
        assertEquals(
            result,
            GetPostResult.GetPostError(
                exception = testException
            )
        )
    }

    @Test
    fun `when authorGateway throws an error then return a wrapped exception`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithCorrectData()
        val authorGateway = givenAuthorGatewayWithException()
        val commentGateway = givenCommentGatewayWithCorrectData()
        val subject = GetPostUseCase(postGateway, authorGateway, commentGateway)

        //Act
        val result = subject.invoke(GetPostParams(postId))

        //Verify
        assertEquals(
            result,
            GetPostResult.GetPostError(
                exception = testException
            )
        )
    }

    @Test
    fun `when commentGateway throws an error then return a wrapped exception`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithCorrectData()
        val authorGateway = givenAuthorGatewayWithCorrectData()
        val commentGateway = givenCommentGatewayWithException()
        val subject = GetPostUseCase(postGateway, authorGateway, commentGateway)

        //Act
        val result = subject.invoke(GetPostParams(postId))

        //Verify
        assertEquals(
            result,
            GetPostResult.GetPostError(
                exception = testException
            )
        )
    }

    private fun givenCommentGatewayWithCorrectData(): CommentGateway = mock {
        onBlocking { getComments(postId) } doReturn testComments
    }

    private fun givenCommentGatewayWithException(): CommentGateway = mock {
        onBlocking { getComments(postId) } doThrow testException
    }

    private fun givenAuthorGatewayWithCorrectData(): AuthorGateway = mock {
        onBlocking { getAuthor(authorId) } doReturn testAuthorEntity
    }

    private fun givenAuthorGatewayWithException(): AuthorGateway = mock {
        onBlocking { getAuthor(authorId) } doThrow testException
    }

    private fun givenAPostGatewayWithCorrectData(): PostGateway = mock {
        onBlocking { getPost(postId) } doReturn testPostEntity
    }

    private fun givenAPostGatewayWithException(): PostGateway = mock {
        onBlocking { getPost(postId) } doThrow testException
    }

    private val authorId = 3
    private val postId = 334

    private val testException = TestException("test")

    private val testPostEntity = PostEntity(
        id = postId,
        body = "body",
        title = "title",
        authorId = authorId
    )

    private val testAuthorEntity = AuthorEntity(
        id = authorId,
        name = "Johny"
    )

    private val testComments = listOf(
        CommentEntity(3), CommentEntity(4)
    )
}

