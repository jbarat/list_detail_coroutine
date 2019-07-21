package co.uk.jbarat.coroutinelistanddetail.domain.getpost

sealed class GetPostResult {
    data class GetPostSuccess(
        val simplePostEntities: DetailedPostEntity
    ) : GetPostResult()

    data class GetPostError(
        val exception: Exception
    ) : GetPostResult()
}
