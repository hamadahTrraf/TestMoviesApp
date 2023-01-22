package com.example.tms_app.Application

import android.app.Application
import com.example.tms_app.DataBase.AppDatabase
import com.example.tms_app.Repositories.MainRepository
import com.example.tms_app.Repositories.SearchRepository
import com.example.tms_app.Repositories.TvRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class tms_app: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val mainRepository by lazy { MainRepository(database.movieDao()) }
    val tvRepository by lazy{ TvRepository(database.tvDao())}
    val searchRepository by lazy{SearchRepository(database.movieDao(), database.tvDao())}
}