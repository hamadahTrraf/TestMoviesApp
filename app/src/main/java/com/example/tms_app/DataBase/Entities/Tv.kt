package com.example.tms_app.DataBase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tv(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "record_id") var record_id: Long = 0,
    @ColumnInfo(name = "poster_path") var poster_path: String?,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "original_name") var original_title: String,
    @ColumnInfo(name = "original_language") var original_language: String,
    @ColumnInfo(name = "backdrop_path") var backdrop_path: String?,
    @ColumnInfo(name = "popularity") var popularity: Float,
    @ColumnInfo(name = "vote_count") var vote_count: Int,
    @ColumnInfo(name = "vote_average") var vote_average: Float,
    @ColumnInfo(name = "isFavourite") var isFavourite: Boolean
)