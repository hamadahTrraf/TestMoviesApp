package com.example.tms_app.Network.Request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ValidateRTokenLoginBody(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String ,
    @Json(name = "request_token") val request_token: String,

)