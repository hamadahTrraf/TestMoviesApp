package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTopRatedTVResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<TVResult>,
    @Json(name = "total_results")val total_results: Int,
    @Json(name = "total_pages") val total_pages: Int

)


@JsonClass(generateAdapter = true)
data class TVResult(
    @Json(name = "poster_path") val poster_path: String?,
    @Json(name = "popularity") val popularity: Float,
    @Json(name = "id") val id: Int,
    @Json(name = "backdrop_path") val backdrop_path: String?,
    @Json(name = "vote_average") val vote_average: Float,
    @Json(name = "overview") val overview: String,
    @Json(name = "first_air_date") val first_air_date: String? = "",//origin_country   genre_ids
    @Json(name = "origin_country") val origin_country: List<String>,
    @Json(name = "genre_ids") val genre_ids: List<String>,
    @Json(name = "original_language") val original_language: String,
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "name") val name: String,
    @Json(name = "original_name") val original_name: String,

)