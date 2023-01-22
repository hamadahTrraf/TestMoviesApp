package com.example.tms_app.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.DataBase.MovieDetailsDb
import com.example.tms_app.DataBase.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao{

    @Query("select * from Movie")
    fun getMovies(): Flow<List<Movie?>>

    @Query("select * from MovieDetailsDb where record_id = (select max(record_id) from MovieDetailsDb) ")
    fun getMoviesWithDetail(): Flow<MovieDetailsDb?>

    @Insert(onConflict = REPLACE)
    suspend fun insertSingleMovie(movie: Movie)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovieDetails(moviedetails: MovieDetailsDb)

    @Query("select * from MovieDetailsDb where record_id = (select max(record_id) from MovieDetailsDb)")
    suspend fun getMovieDetails(): MovieDetailsDb

    @Query("delete from Movie")
    suspend fun emptyTheTable()

    @Query("delete from UserInfo")
    suspend fun deleteUserInfo()

    @Insert(onConflict = REPLACE)
    suspend fun insertUserInfo(user: UserInfo)

    @Query("select sessionId from UserInfo where record_id = (select max(record_id) from UserInfo)")
    suspend fun getSessionId(): String

    @Query("select * from Movie where isFavourite = 1")
    suspend fun tesTPurpose(): Movie?

    @Query("update Movie set isFavourite = :isFavourite where id = :movie_id ")
    suspend fun updateFavouriteOfMovie(isFavourite: Boolean, movie_id: Int)

    @Query("select userName from UserInfo where record_id = (select max(record_id) from UserInfo)")
    suspend fun getUserName(): String
}