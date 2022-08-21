package com.myapp.simplegallery.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.myapp.simplegallery.database.MoviesTable
import com.myapp.simplegallery.models.MoviesModelItem

@Dao
interface Dao {

    //createNewWheel Table
    @Insert
    fun insertInMoviesTable(moviesTable: MoviesTable): Long

    @Query("select * from moviesTable")
    fun getMoviesTable() : List<MoviesTable>
}