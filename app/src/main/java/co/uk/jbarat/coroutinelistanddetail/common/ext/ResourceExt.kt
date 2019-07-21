package co.uk.jbarat.coroutinelistanddetail.common.ext

import android.content.res.Resources
import androidx.annotation.DimenRes
import kotlin.math.roundToInt

fun Resources.getDimensionInPixel(@DimenRes id: Int) =
        (getDimension(id) * Resources.getSystem().displayMetrics.density).roundToInt()