package co.uk.jbarat.coroutinelistanddetail.domain.common

interface UseCase<in Params, out Result> {
    suspend fun invoke(params: Params): Result
}

interface NoParamUseCase<out Result> {
    suspend fun invoke(): Result
}
