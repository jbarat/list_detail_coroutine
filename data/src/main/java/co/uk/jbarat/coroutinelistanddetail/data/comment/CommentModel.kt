package co.uk.jbarat.coroutinelistanddetail.data.comment

import com.squareup.moshi.Json

data class CommentModel(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "postId") val postId: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "body") val body: String
)
