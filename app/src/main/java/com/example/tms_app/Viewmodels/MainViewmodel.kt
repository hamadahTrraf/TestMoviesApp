package com.example.tms_app.Viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.DataBase.MovieDetailsDb
import com.example.tms_app.DataBase.UserInfo
import com.example.tms_app.Network.Request.SessionId
import com.example.tms_app.Network.Response.MovieDetails
import com.example.tms_app.Repositories.MainRepository
import com.example.watchme.Network.GetMoviesResponse
import com.example.watchme.Network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(val mainRepository: MainRepository): ViewModel() {
    private val _movies: MutableStateFlow<List<Movie?>> = MutableStateFlow(arrayListOf())
    val movies: StateFlow<List<Movie?>>
        get() = _movies
    private var _isLogged: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean>
        get() = _isLogged
    private val _MovieWithDetail: MutableStateFlow<MovieDetailsDb?> = MutableStateFlow(null)
    val MovieWithDetail: StateFlow<MovieDetailsDb?>
        get() = _MovieWithDetail
    val api_key = "9c0412867f5e586e1eb9d1ae58f4f89d"

    init {
        Log.e("mainViewModel","started")
        viewModelScope.launch {
            mainRepository.movies.collect{
                _movies.value = it
            }
        }
        viewModelScope.launch {
            mainRepository.MovieWithDetail.collect{
                _MovieWithDetail.value = it
            }
        }
    }
    fun getTopRatedMovies(pageNumber: Int?){
        viewModelScope.launch {
            insertMoviesIntoDataBase()
        }
    }
    suspend fun getUserName(): String{
        return mainRepository.getUserName()
    }

    fun setFavouriteOn(movie_id: Int, type: String){
        viewModelScope.launch {
            val sessionId = mainRepository.getSessionId()
            mainRepository.setFavouriteOn(movie_id, api_key,sessionId,type)
        }
    }
    fun setFavouriteOff(movie_id: Int, type: String){
        viewModelScope.launch {
            val sessionId = mainRepository.getSessionId()
            mainRepository.setFavouriteOff(movie_id, api_key,sessionId,type)
        }
    }

    suspend fun insertMoviesIntoDataBase(){
        mainRepository.emptyTheTable()
        val session_number = mainRepository.getSessionId()
        mainRepository.getMoviesAndFlagThem(api_key,session_number)
    }

    suspend fun emptyMoviesFromDatyaBase(){
        mainRepository.emptyTheTable()
    }

    suspend fun cleanCach(){
        mainRepository.emptyTheTable()
    }

    suspend fun search(){

    }

     fun login(userName: String, password: String){
         viewModelScope.launch {
             mainRepository.requestUnApprovalRToken(api_key)
                 .onSuccess { result ->
                     if (result.success) {
                         mainRepository.validateRequestTokenWithLogin(
                             api_key,
                             userName,
                             password,
                             result.request_token
                         )
                             .onSuccess { approval ->
                                 Log.e("request_token", approval.request_token)
                                 Log.e("api_key", api_key)
                                 mainRepository.createSessionKey(api_key, approval.request_token)
                                     .onSuccess {
                                             session ->
                                         mainRepository.getUserInfoFromServer(api_key, session.session_id)
                                             .onSuccess {
                                                 Log.e("success","user_id:  ${it.id}, userName: ${it.name}, session_id: ${session.session_id}")
                                                 mainRepository.insertUserInfo(
                                                     UserInfo(
                                                         record_id = 0,
                                                         userId = it.id,
                                                         userName = it.username,
                                                         sessionId = session.session_id,
                                                         approvedRToken = approval.request_token
                                                     )
                                                 )
                                                 _isLogged.value = true
                                                 Log.e("_isLogged", _isLogged.value.toString())
                                             }
                                             .onFailure {
                                                 Log.e("myErrors","1>>>> ${it.message}")
                                             }
                                     }
                                     .onFailure {
                                         Log.e("myErrors","2>>>> ${it.message}")
                                     }

                             }
                             .onFailure {
                                 Log.e("myErrors","3>>>> ${it.message}")
                             }
                     }
                 }
                 .onFailure {
                     Log.e("myErrors","4>>>> ${it.message}")
                 }
         }
     }

    fun logOut(){
        viewModelScope.launch {
            val sessionId = mainRepository.getSessionId()
            Retrofit.retrofitService.logout(api_key, SessionId(sessionId))
                .onSuccess {
                    mainRepository.clearUserInfo()
                    _isLogged.value = false
                }

        }
    }

    suspend fun getMovieDetailsByNetwork( movieId: Int){
         mainRepository.getMovieDetailsFromNetwork(api_key, movieId)
             .onSuccess {
                 saveMovieDetailsInDb(
                     MovieDetailsDb(
                         0,
                         it.title,
                         it.vote_count,
                         it.status,
                         it.original_title,
                         it.release_date,
                         it.budget,
                         it.overview,
                         it.popularity,
                         it.poster_path,
                         it.runtime?.toInt()
                     )
                 )
             }
             .onFailure {
                 Log.e("failurssdsdsde",it.message.toString())
             }

    }

    suspend fun getMovieDetailsFromDb(): MovieDetailsDb{
        return mainRepository.getMovieDetailsFromDb()
    }

    suspend fun saveMovieDetailsInDb(movieDetailsDb: MovieDetailsDb){
        mainRepository.saveMovieDetails(movieDetailsDb)
    }

}


