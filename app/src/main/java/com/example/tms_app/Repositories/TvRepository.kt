package com.example.tms_app.Repositories

import android.util.Log
import com.example.tms_app.DataBase.Dao.TvDao
import com.example.tms_app.DataBase.Entities.Tv
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.Network.Request.MarkMovieBody
import com.example.tms_app.Network.Response.GetTopRatedTVResponse
import com.example.watchme.Network.GetMoviesResponse
import com.example.watchme.Network.Retrofit
import kotlinx.coroutines.flow.Flow

class TvRepository(val tvDao: TvDao) {
    val tvs: Flow<List<Tv?>> = tvDao.getTv()

    suspend fun getTopRatedTvs(apiKey: String, language: String? = "", page: Int? = 1): Result<GetTopRatedTVResponse>{
        Log.e("apiKey",apiKey)
        Log.e("language","kk" + language)
        Log.e("language","kk" + language)
        return Retrofit.retrofitService.getTopRatedSeries(apiKey,"",1)
    }

    suspend fun insertTvIntoDataBase(tvs: List<Tv?>){
        for(tv in tvs){
            if(tv != null){
                tvDao.insertSingleTv(tv)
            }
        }
    }

    suspend fun getTvsAndFlagThem(apiKey: String, session_id: String, pageMovie: Int?= 1){
        Log.e("hhhg","2")
        var favouriteTvs = mutableListOf<Int>()
        Retrofit.retrofitService.getFavouriteTV(apiKey, session_id)
            .onSuccess {
                Log.e("hhhg","13")
                for(tv in it.results){
                    favouriteTvs.add(tv.id)
                }
                Log.e("longOfP",favouriteTvs.size.toString())
                if(it.total_pages > 1){
                    for(page_number in 2.. it.total_pages){
                        Retrofit.retrofitService.getFavouriteTV(apiKey, session_id,page_number)
                            .onSuccess {collection->
                                for(tv in collection.results){
                                    favouriteTvs.add(tv.id)
                                }
                            }
                    }
                }
            }
            .onFailure {
                Log.e("jdjd",it.message.toString())
            }
        Log.e("hhhg","23")
        var tvsWithFlag = mutableListOf<Tv>()
        getTopRatedTvs(apiKey = apiKey, page = pageMovie)
            .onSuccess {
                for(tv in it.results){
                    Log.e("addf", "movie_id = ${tv.id}")
                    tvsWithFlag.add(
                        Tv(
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
                            favouriteTvs.any { favouriteTv -> favouriteTv == tv.id }
                        )
                    )
                }
                insertTvIntoDataBase(tvsWithFlag)
            }
            .onFailure {
                Log.e("failes", "Failure in the Tv get Movies operation.")
                Log.e("failes", it.message.toString())
            }
    }

    suspend fun getSessionId(): String{
        return tvDao.getSessionId()
    }

    suspend fun emptyTheTable(){
        tvDao.emptyTheTable()
    }

    suspend fun setFavouriteOff(tv_id: Int, api_key: String, session_id: String, type: String){
        tvDao.updateFavouriteOfTv(false, tv_id)
        Retrofit.retrofitService.markMovie(api_key,session_id, MarkMovieBody(type,tv_id,false))
            .onFailure {
                tvDao.updateFavouriteOfTv(true, tv_id)
            }
    }

    suspend fun setFavouriteOn(tv_id: Int, api_key: String, session_id: String, type: String){
        tvDao.updateFavouriteOfTv(true, tv_id)
        Retrofit.retrofitService.markMovie(api_key,session_id, MarkMovieBody(type,tv_id,true))
            .onFailure {
                tvDao.updateFavouriteOfTv(false, tv_id)
            }
    }




}