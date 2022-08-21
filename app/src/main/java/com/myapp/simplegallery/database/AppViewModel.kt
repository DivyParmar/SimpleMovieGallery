package com.myapp.simplegallery.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.simplegallery.interfaces.RetrofitService
import com.myapp.simplegallery.models.MoviesModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel(val repository: Repository) : ViewModel() {

    val movieList = MutableLiveData<List<MoviesModelItem>>()
    val errorMessage = MutableLiveData<String>()
    val successInsertInMoviesTable = MutableLiveData<Long>()
    val resultMoviesTable = MutableLiveData<List<MoviesTable>>()

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllMovies()
            response.enqueue(object : Callback<List<MoviesModelItem>> {
                override fun onResponse(call: Call<List<MoviesModelItem>>, response: Response<List<MoviesModelItem>>) {
                    movieList.postValue(response.body())
                }
                override fun onFailure(call: Call<List<MoviesModelItem>>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun insertInMoviesTable(moviesTable: MoviesTable){
        viewModelScope.launch(Dispatchers.IO) {
            successInsertInMoviesTable.postValue(repository.insertInMoviesTable(moviesTable))
        }
    }

    fun getMoviesTable(){
        viewModelScope.launch(Dispatchers.IO) {
            resultMoviesTable.postValue(repository.getMoviesTable())
        }
    }
}