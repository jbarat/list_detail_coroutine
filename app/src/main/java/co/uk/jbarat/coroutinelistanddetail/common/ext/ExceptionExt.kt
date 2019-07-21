package co.uk.jbarat.coroutinelistanddetail.common.ext

/**In a real app the message should be mapped to something more user friendly */
fun Exception.toPresentation() = "Error $message"
