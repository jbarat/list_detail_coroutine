package co.uk.jbarat.coroutinelistanddetail.common.ext

import android.view.View

fun View.show(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}