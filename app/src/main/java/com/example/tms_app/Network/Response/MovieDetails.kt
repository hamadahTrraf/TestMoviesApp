package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetails(
    @Json(name = "title") val title: String,
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "status") val status: String,
    @Json(name = "original_title") val original_title: String,
    @Json(name = "release_date") val release_date: String,
    @Json(name = "budget") val budget: Int,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Float,
    @Json(name = "poster_path") val poster_path: String?,
    @Json(name = "runtime") val runtime: Int?,


)