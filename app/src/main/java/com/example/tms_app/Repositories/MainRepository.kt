package com.example.tms_app.Repositories

import android.util.Log
import com.example.tms_app.DataBase.Dao.MovieDao
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.DataBase.MovieDetailsDb
import com.example.tms_app.DataBase.UserInfo
import com.example.tms_app.Network.Request.ApprovedRequestToken
import com.example.tms_app.Network.Request.MarkMovieBody
import com.example.tms_app.Network.Request.ValidateRTokenLoginBody
import com.example.tms_app.Network.Response.*
import com.example.watchme.Network.GetMoviesResponse
import com.example.watchme.Network.Retrofit
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow

class MainRepository(val movieDao: MovieDao) {
    val movies: Flow<List<Movie?>> = movieDao.getMovies()
    val MovieWithDetail: Flow<MovieDetailsDb?> = movieDao.getMoviesWithDetail()

    suspend fun getTopRatedMovies(apiKey: String, language: String? = "", page: Int? = 1, region: String? = "" ): Result<GetMoviesResponse>{
        return Retrofit.retrofitService.getTopRatedMovies(apiKey,language,page,region)
    }

    suspend fun insertMoviesIntoDataBase(movies: List<Movie?>){
        for(movie in movies){
            if(movie != null){
                movieDao.insertSingleMovie(movie)
            }
        }
    }

    suspend fun getSessionId(): String{
        return movieDao.getSessionId()
    }

    suspend fun emptyTheTable(){
        movieDao.emptyTheTable()
    }

    suspend fun requestUnApprovalRToken(api_key: String): Result<GetUnApprovalRequestToken> {
        return Retrofit.retrofitService.getUnApprovedRequestToken(api_key)
    }

    suspend fun validateRequestTokenWithLogin(apiKey: String,userName: String, password: String, unUprovalRToken: String):Result<ValidateRTokenLoginResponse>{
        return Retrofit.retrofitService.validateRequestTokenWithLogin(apiKey, ValidateRTokenLoginBody(userName, password,unUprovalRToken)
        )
    }

    suspend fun createSessionKey(api_key: String, approvedRequestToken: String ): Result<CreateSessionIdResponse>{
        return Retrofit.retrofitService.createSessionKey(api_key, ApprovedRequestToken(approvedRequestToken) )
    }

    suspend fun getUserInfoFromServer(api_key: String, session_id: String): Result<getUserInfoFromServerResponse>{
        return Retrofit.retrofitService.getUserInfoFromServer(api_key, session_id)
    }

    suspend fun insertUserInfo(user: UserInfo){
        movieDao.insertUserInfo(user)
    }

    // bring all favourite movies and keep it in hand, bring the first page of atop rated movies and test every movie
    // id if belongs to the favourite movie ids, if yes flag on as favourite, else flag down.
    suspend fun getMoviesAndFlagThem(apiKey: String, session_id: String, pageMovie: Int?= 1){
        var favouriteMovies = mutableListOf<Int>()
        Retrofit.retrofitService.getFavouriteMovies(apiKey, session_id)
            .onSuccess {
                for(movie in it.results){
                    favouriteMovies.add(movie.id)
                }
                Log.e("longOfP",favouriteMovies.size.toString())
                if(it.total_pages > 1){
                    for(page_number in 2.. it.total_pages){
                        Retrofit.retrofitService.getFavouriteMovies(apiKey, session_id,page_number)
                            .onSuccess {collection->
                                for(movie in collection.results){
                                    favouriteMovies.add(movie.id)
                                }
                            }
                    }
                }
            }
            .onFailure {
                Log.e("jdjd",it.message.toString())
            }
        var moviesWithFlag = mutableListOf<Movie>()
        getTopRatedMovies(apiKey = apiKey, page = pageMovie)
            .onSuccess {
                for(movie in it.results){
                    Log.e("addf", "movie_id = ${movie.id}")
                        moviesWithFlag.add(
                            Movie(
                                0,
                               movie.poster_path,
                               movie.adult,
                               movie.overview,
                               movie.release_date,
                               movie.id,
                               movie.original_title,
                               movie.original_language,
                               movie.title,
                               movie.backdrop_path,
                               movie.popularity,
                               movie.vote_count,
                               movie.video,
                               movie.vote_average,
                                favouriteMovies.any { favouriteMovie -> favouriteMovie == movie.id }
                            )
                        )
                    }
                insertMoviesIntoDataBase(moviesWithFlag)
                }
    }

    suspend fun setFavouriteOff(movie_id: Int, api_key: String, session_id: String, type: String){
        movieDao.updateFavouriteOfMovie(false, movie_id)
        Retrofit.retrofitService.markMovie(api_key,session_id, MarkMovieBody(type,movie_id,false))
            .onFailure {
                movieDao.updateFavouriteOfMovie(true, movie_id)
            }
    }

    suspend fun setFavouriteOn(movie_id: Int, api_key: String, session_id: String, type: String){
        movieDao.updateFavouriteOfMovie(true, movie_id)
        Retrofit.retrofitService.markMovie(api_key,session_id, MarkMovieBody(type,movie_id,true))
            .onFailure {
                movieDao.updateFavouriteOfMovie(false, movie_id)
            }
    }

    suspend fun clearUserInfo(){
        movieDao.deleteUserInfo()
    }

    suspend fun getUserName(): String{
        return movieDao.getUserName()
    }

    suspend fun getMovieDetailsFromNetwork(api_key: String, movieId: Int): Result<MovieDetails>{
        return Retrofit.retrofitService.getMovieDetails(movieId,api_key)
    }

    suspend fun saveMovieDetails(movieDetails: MovieDetailsDb){
        movieDao.insertMovieDetails(movieDetails)
    }

    suspend fun getMovieDetailsFromDb(): MovieDetailsDb{
        return movieDao.getMovieDetails()
    }



}