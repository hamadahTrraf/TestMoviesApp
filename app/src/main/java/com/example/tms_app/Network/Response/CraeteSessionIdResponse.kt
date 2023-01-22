package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CreateSessionIdResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "session_id") val session_id: String,

)