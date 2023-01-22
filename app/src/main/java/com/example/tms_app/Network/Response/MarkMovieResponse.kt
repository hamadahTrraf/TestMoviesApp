package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarkMovieResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "status_code") val status_code: String,
    @Json(name = "status_message") val status_message: String

)
