package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidateRTokenLoginResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "expires_at") val expires_at: String,
    @Json(name = "request_token") val request_token: String

)