package com.example.tms_app.Network.Response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



    data class GetFavouriteTVResult(
        @Json(name = "page") val page: Int,
        @Json(name = "results") val results: List<TVFavouriteResult>,
        @Json(name = "total_results")val total_results: Int,
        @Json(name = "total_pages") val total_pages: Int

    )


    @JsonClass(generateAdapter = true)
    data class TVFavouriteResult(
        @Json(name = "id") val id: Int,
    )

