package com.example.tms_app.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tms_app.Repositories.MainRepository
import com.example.tms_app.Repositories.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(val searchRepository: SearchRepository): ViewModel() {
    val api_key = "9c0412867f5e586e1eb9d1ae58f4f89d"
     fun search(query : String){
         viewModelScope.launch {
             searchRepository.Search(api_key, query)
         }
    }
}