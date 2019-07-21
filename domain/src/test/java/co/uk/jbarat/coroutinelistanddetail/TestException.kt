package co.uk.jbarat.coroutinelistanddetail

data class TestException(override val message: String) : RuntimeException(message)