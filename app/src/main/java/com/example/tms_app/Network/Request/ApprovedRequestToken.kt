package com.example.tms_app.Network.Request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApprovedRequestToken(
    @Json(name = "request_token") val request_token: String,
)


@JsonClass(generateAdapter = true)
data class SessionId(
    @Json(name = "session_id") val session_id: String,
)