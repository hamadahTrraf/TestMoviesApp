package com.example.tms_app.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "record_id") var record_id: Long = 0,
    @ColumnInfo(name = "poster_path") var poster_path: String?,
    @ColumnInfo(name = "adult") var adult: Boolean,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "release_date") var release_date: String,
    @ColumnInfo(name =  "id") var id: Int,
    @ColumnInfo(name = "original_title") var original_title: String,
    @ColumnInfo(name = "original_language") var original_language: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "backdrop_path") var backdrop_path: String?,
    @ColumnInfo(name = "popularity") var popularity: Float,
    @ColumnInfo(name = "vote_count") var vote_count: Int,
    @ColumnInfo(name = "video") var video: Boolean?,
    @ColumnInfo(name = "vote_average") var vote_average: Float,
    @ColumnInfo(name = "isFavourite") var isFavourite: Boolean
)

@Entity
data class MovieDetailsDb(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "record_id") var record_id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "vote_count") var vote_count: Int,
    @ColumnInfo(name = "status_message") var status_message: String,
    @ColumnInfo(name = "original_title") var original_title: String,
    @ColumnInfo(name = "release_date") var release_date: String,
    @ColumnInfo(name = "budget") var budget: Int,
    @ColumnInfo(name = "overview") var overview: String?,
    @ColumnInfo(name = "popularity") var popularity: Float,
    @ColumnInfo(name = "poster_path") var poster_path: String?,
    @ColumnInfo(name = "runtime") var runtime: Int?,
)

@Entity
data class UserInfo(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "record_id") var record_id: Long = 0,
    @ColumnInfo(name = "userId") var userId: Int,
    @ColumnInfo(name = "userName") var userName: String,
    @ColumnInfo(name = "sessionId") var sessionId: String,
    @ColumnInfo(name = "approvedRToken") var approvedRToken: String,

)