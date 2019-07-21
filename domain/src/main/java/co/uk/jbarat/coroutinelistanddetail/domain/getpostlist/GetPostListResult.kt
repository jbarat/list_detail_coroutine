package co.uk.jbarat.coroutinelistanddetail.domain.getpostlist

sealed class GetPostListResult {
    data class GetPostListSuccess(
            val simplePostEntities: List<SimplePostEntity>
    ) : GetPostListResult()

    data class GetPostListError(
            val exception: Exception
    ) : GetPostListResult()
}
