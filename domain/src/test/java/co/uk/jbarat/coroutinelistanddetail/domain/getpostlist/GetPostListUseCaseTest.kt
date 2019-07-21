package co.uk.jbarat.coroutinelistanddetail.domain.getpostlist

import co.uk.jbarat.coroutinelistanddetail.TestException
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostEntity
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostGateway
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPostListUseCaseTest {

    @Test
    fun `when data available from every gateway then return data mapped`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithCorrectData()
        val authorGateway = givenAuthorGatewayWithCorrectData()
        val subject = GetPostListUseCase(postGateway, authorGateway)

        //Act
        val result = subject.invoke()

        //Verify
        assertEquals(
                result,
                GetPostListResult.GetPostListSuccess(
                        listOf(
                                SimplePostEntity(
                                        id = testPostsEntity[0].id,
                                        body = testPostsEntity[0].body,
                                        title = testPostsEntity[0].title,
                                        author = testAuthor1Entity
                                ),
                                SimplePostEntity(
                                        id = testPostsEntity[1].id,
                                        body = testPostsEntity[1].body,
                                        title = testPostsEntity[1].title,
                                        author = testAuthor2Entity
                                )
                        )
                )
        )
    }

    @Test
    fun `when postGateway throws an error then return a wrapped exception`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithException()
        val authorGateway = givenAuthorGatewayWithCorrectData()
        val subject = GetPostListUseCase(postGateway, authorGateway)

        //Act
        val result = subject.invoke()

        //Verify
        assertEquals(
                result,
                GetPostListResult.GetPostListError(
                        testException
                )
        )
    }

    @Test
    fun `when authorGateway throws an error then return a wrapped exception`() = runBlocking {
        //Given
        val postGateway = givenAPostGatewayWithCorrectData()
        val authorGateway = givenAuthorGatewayWithException()
        val subject = GetPostListUseCase(postGateway, authorGateway)

        //Act
        val result = subject.invoke()

        //Verify
        assertEquals(
                result,
                GetPostListResult.GetPostListError(
                        testException
                )
        )
    }

    private fun givenAPostGatewayWithCorrectData(): PostGateway = mock {
        onBlocking { getAllPost() } doReturn testPostsEntity
    }

    private fun givenAPostGatewayWithException(): PostGateway = mock {
        onBlocking { getAllPost() } doThrow testException
    }

    private fun givenAuthorGatewayWithCorrectData(): AuthorGateway = mock {
        onBlocking { getAuthor(authorId1) } doReturn testAuthor1Entity
        onBlocking { getAuthor(authorId2) } doReturn testAuthor2Entity
    }

    private fun givenAuthorGatewayWithException(): AuthorGateway = mock {
        onBlocking { getAuthor(authorId1) } doThrow testException
    }

    private val authorId1 = 3
    private val authorId2 = 4

    private val testException = TestException("test")

    private val testPostsEntity = listOf(
            PostEntity(
                    id = 334,
                    body = "body",
                    title = "title",
                    authorId = authorId1
            ),
            PostEntity(
                    id = 3345,
                    body = "body2",
                    title = "title2",
                    authorId = authorId2
            )
    )

    private val testAuthor1Entity = AuthorEntity(
            id = authorId1,
            name = "Johny"
    )

    private val testAuthor2Entity = AuthorEntity(
            id = authorId2,
            name = "Johny Dee"
    )
}