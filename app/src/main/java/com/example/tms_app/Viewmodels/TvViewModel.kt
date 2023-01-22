package com.example.tms_app.Viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms_app.DataBase.Entities.Tv
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.Repositories.MainRepository
import com.example.tms_app.Repositories.TvRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TvViewModel(val tvRepository: TvRepository): ViewModel() {
    private val _tv: MutableStateFlow<List<Tv?>> = MutableStateFlow(arrayListOf())
    val tv: StateFlow<List<Tv?>>
        get() = _tv
    val api_key = "9c0412867f5e586e1eb9d1ae58f4f89d"

    init {
        Log.e("TvViewModel","started")
        viewModelScope.launch {
            tvRepository.tvs.collect{
                _tv.value = it
            }
        }
    }

    fun getTopRatedTv(pageNumber: Int?){
        Log.e("hhhg","133")
        viewModelScope.launch {
            insertTvsIntoDataBase()
        }
    }
    suspend fun insertTvsIntoDataBase(){
        Log.e("hhhg","1")
        tvRepository.emptyTheTable()
        val sessionNumber = tvRepository.getSessionId()
        tvRepository.getTvsAndFlagThem(api_key,sessionNumber)
    }

    suspend fun emptyTvsFromDatyaBase(){
        tvRepository.emptyTheTable()

    }

    fun setFavouriteOn(tv_id: Int, type: String){
        viewModelScope.launch {
            val sessionId = tvRepository.getSessionId()
            tvRepository.setFavouriteOn(tv_id, api_key,sessionId,type)
        }
    }
    fun setFavouriteOff(tv_id: Int, type: String){
        viewModelScope.launch {
            val sessionId = tvRepository.getSessionId()
            tvRepository.setFavouriteOff(tv_id, api_key,sessionId,type)
        }
    }


}