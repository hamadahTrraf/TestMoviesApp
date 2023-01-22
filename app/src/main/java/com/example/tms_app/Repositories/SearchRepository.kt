package com.example.tms_app.Repositories

import android.util.Log
import com.example.tms_app.DataBase.Dao.MovieDao
import com.example.tms_app.DataBase.Dao.TvDao
import com.example.tms_app.DataBase.Entities.Tv
import com.example.tms_app.DataBase.Movie
import com.example.watchme.Network.Retrofit

class SearchRepository(val movieDao: MovieDao, val tvDao: TvDao) {

    suspend fun Search(api_key: String, query: String){
        var singleMovie: Movie
        var singleTv: Tv
        movieDao.emptyTheTable()
        tvDao.emptyTheTable()
        Retrofit.retrofitService.searchForMovie(api_key,query)
            .onSuccess {
                Log.e("success","100")
                movieDao.emptyTheTable()
                for(movie in it.results){
                    singleMovie = Movie(
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
                        false
                    )
                    movieDao.insertSingleMovie(singleMovie)
                }

            }
            .onFailure {
                Log.e("Failure","100" + "  , " + it.message.toString())
            }
        Retrofit.retrofitService.searchForTv(api_key, query)
            .onSuccess {
                Log.e("success","1200")
                for(tv in it.results){
                    singleTv = Tv(
                        0,
                        tv.poster_path,
                        tv.overview,
                        tv.id,
                        tv.original_name,
                        tv.original_language,
                        tv.backdrop_path,
                        tv.popularity,
                        tv.vote_count,
                        tv.popularity,
                        false
                    )
                    tvDao.insertSingleTv(singleTv)
                }
            }
            .onFailure {
                Log.e("Failure","1200" + "  , " + it.message.toString())
            }
    }
}