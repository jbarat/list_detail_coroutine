package co.uk.jbarat.coroutinelistanddetail.data.author

import com.squareup.moshi.Json

data class AuthorModel(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String
)
