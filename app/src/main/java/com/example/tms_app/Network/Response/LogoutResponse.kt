package com.example.tms_app.Network.Response

import com.squareup.moshi.Json


data class LogoutResponse(
    @Json(name = "success") val success: Boolean, )