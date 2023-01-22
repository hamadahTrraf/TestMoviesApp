package com.example.tms_app

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.tms_app.AppUi.MoviesUi.StartApp
import com.example.tms_app.Application.tms_app
import com.example.tms_app.Viewmodels.MainViewModel
import com.example.tms_app.Viewmodels.SearchViewModel
import com.example.tms_app.Viewmodels.TvViewModel
import com.example.watchme.Network.GetMoviesRequest
import com.example.watchme.Network.Retrofit
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext


class MainActivity : ComponentActivity() {
    lateinit var  mainViewModel: MainViewModel
    lateinit var  tvViewModel: TvViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var  coroutineScope: LifecycleCoroutineScope
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         coroutineScope = lifecycleScope
         mainViewModel =
            MainViewModel((application as tms_app).mainRepository)
        tvViewModel =
            TvViewModel(((application as tms_app).tvRepository))
        searchViewModel =
            SearchViewModel(((application as tms_app).searchRepository))
        coroutineScope.launch{
            mainViewModel.cleanCach()
        }
        setContent {
                androidx.compose.material3.Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*LaunchedEffect(key1 = true){
                        mainViewModel.login("hamadahTrraf","12345")
                    }*/

                    StartApp(mainViewModel,tvViewModel, searchViewModel)
                }
        }

    }
    override fun onDestroy(){
       super.onDestroy()
        coroutineScope.launch{
            mainViewModel.cleanCach()
        }
    }

}









