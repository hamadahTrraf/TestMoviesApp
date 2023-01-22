package com.example.watchme.Network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetMoviesRequest(
    @Json(name = "api_key") val api_key: String,
    @Json(name = "language") val language: String? = "",
    @Json(name = "page") val page: Int? = 1,
    @Json(name = "region") val region: String? = ""

)
