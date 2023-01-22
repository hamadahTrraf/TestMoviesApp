package com.example.tms_app.Network.Request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarkMovieBody(
    @Json(name = "media_type") val media_type: String,
    @Json(name = "media_id") val media_id: Int,
    @Json(name = "favorite") val favorite: Boolean,
    )