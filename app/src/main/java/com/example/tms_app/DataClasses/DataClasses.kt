package com.example.tms_app.DataClasses

data class MovieInitialInfo(
    val id: Int,
    val image: Int,
    val title: String,
    val popularity: String,
    val release_date: String,
    val isFavourite: Boolean,
    val uri: String?
)


data class TvInitialInfo(
    val id: Int,
    val image: Int,
    val name: String,
    val popularity: String,
    val isFavourite: Boolean,
    val uri: String?
)