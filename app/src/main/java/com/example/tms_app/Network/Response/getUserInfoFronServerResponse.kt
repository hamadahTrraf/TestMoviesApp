package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class getUserInfoFromServerResponse(
    @Json(name = "avatar") val avatar: Avatar,
    @Json(name = "id") val id: Int,
    @Json(name = "iso_639_1")val iso_639_1: String,
    @Json(name = "iso_3166_1") val total_pages: String,
    @Json(name = "name") val name: String,
    @Json(name = "include_adult") val include_adult: Boolean,
    @Json(name = "username") val username: String,

)

@JsonClass(generateAdapter = true)
data class Gravatar(
    @Json(name = "hash") val hash: String,
)
@JsonClass(generateAdapter = true)
data class Avatar(
    @Json(name = "gravatar") val gravatar: Gravatar,
)



