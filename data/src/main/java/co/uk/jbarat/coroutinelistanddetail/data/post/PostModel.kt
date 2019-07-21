package co.uk.jbarat.coroutinelistanddetail.data.post

import com.squareup.moshi.Json

data class PostModel(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "userId") val userId: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "body") val body: String
)
