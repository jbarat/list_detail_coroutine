package co.uk.jbarat.coroutinelistanddetail.common.ext

/**Force a when to be exhaustive*/
val <T> T.exhaustive: T
    get() = this

