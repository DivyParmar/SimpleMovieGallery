package com.myapp.simplegallery.database

import android.app.Application
import com.myapp.simplegallery.interfaces.Dao
import com.myapp.simplegallery.interfaces.RetrofitService
import com.myapp.simplegallery.models.MoviesModelItem

class Repository(application: Application,private val retrofitService: RetrofitService) {

    val dao = AppDatabase.getInstance(application).dao()
    fun getAllMovies() = retrofitService.getAllMovies()


    suspend fun insertInMoviesTable(moviesTable: MoviesTable): Long{
        return dao.insertInMoviesTable(moviesTable)
    }


    fun getMoviesTable() : List<MoviesTable> = dao.getMoviesTable()
}