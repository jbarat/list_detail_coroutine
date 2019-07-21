package co.uk.jbarat.coroutinelistanddetail.domain.common

/** I admit, these interfaces are not that useful in this app,
 * but no usecase is complete without an interface using some Generics. */

interface UseCase<in Params, out Result> {
    suspend fun invoke(params: Params): Result
}

interface NoParamUseCase<out Result> {
    suspend fun invoke(): Result
}
